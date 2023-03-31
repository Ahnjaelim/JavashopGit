package kr.co.javashop.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.javashop.dto.PageRequestDTO;
import kr.co.javashop.dto.PageResponseDTO;
import kr.co.javashop.dto.ProductDTO;
import kr.co.javashop.dto.ProductListAllDTO;
import kr.co.javashop.dto.WishDTO;
import kr.co.javashop.security.dto.MemberSecurityDTO;
import kr.co.javashop.service.ProductService;
import kr.co.javashop.service.WishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
	
	@Value("${upload.path}") // application.properties 값 불러오기
	private String uploadPath;
	
	private final ProductService productService;
	private final WishService wishService;
	
	@GetMapping("/list")
	public void list(PageRequestDTO pageRequestDTO, Model model) {
		log.info("<Product Controller> list GET");
		// PageResponseDTO<ProductDTO> responseDTO = productService.list(pageRequestDTO);
		// PageResponseDTO<ProductListReviewCountDTO> responseDTO = productService.listWithReviewCount(pageRequestDTO);
		PageResponseDTO<ProductListAllDTO> responseDTO = productService.listWithAll(pageRequestDTO);
		log.info(responseDTO);
		model.addAttribute("responseDTO", responseDTO);
	}
	
	// @PreAuthorize("principal.username == 'admin'")
	@GetMapping("/register")
	public void registerGET(String prodId, Model model) {
		ProductDTO productDTO = null;
		if(prodId == "" || prodId == null) {
			log.info("<Product Controller> register GET");
			productDTO = ProductDTO.builder()
					.prodId(0L)
					.prodName("")
					.cateCode("")
					.prodPrice(0)
					.prodStock(0)
					.prodDesc("")
					.prodHit(0)
					.build();
			model.addAttribute("dto", productDTO);
		}else {
			log.info("<Product Controller> modify GET");
			productDTO = productService.readOne(Long.valueOf(prodId).longValue());
			model.addAttribute("dto", productDTO);
		}
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/register")
	public String registerPOST(@Valid ProductDTO productDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		log.info("<Product Controller> register POST");
		if(bindingResult.hasErrors()) {
			log.info("has error!");
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			return "redirect:/product/register";
		}
		
		log.info(productDTO);
		Long prodId = productService.register(productDTO);
		redirectAttributes.addFlashAttribute("result", prodId);
		return "redirect:/product/read?prodId="+prodId;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/modify")
	public String modifyPOST(ProductDTO productDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		log.info("<Product Controller> modify POST");
		if(bindingResult.hasErrors()) {
			log.info("has error!");
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			return "redirect:/product/register?prodId="+productDTO.getProdId();	
		}		
		productService.modify(productDTO);
		redirectAttributes.addFlashAttribute("result", "modified");
		return "redirect:/product/read?prodId="+productDTO.getProdId();		
	}
	
	@GetMapping("/read")
	public void read(Long prodId, PageRequestDTO pageRequestDTO, Model model) {
		// MemberSecurityDTO memberSecurityDTO = (MemberSecurityDTO) authentication.getPrincipal();
		log.info("<Product Controller> read GET");
		ProductDTO productDTO = productService.readOne(prodId);
		log.info(productDTO);		
		model.addAttribute("dto", productDTO);
		
		// 찜 상태
		// Long resultCode = wishService.checkWish(memberSecurityDTO.getMid(), productDTO.getProdId());
		model.addAttribute("wish", "");
		// WishDTO wishDTO = wishService.readOne(memberSecurityDTO.getMid(), productDTO.getProdId());
		model.addAttribute("wishdto", "");
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/remove")
	public String remove(ProductDTO productDTO, RedirectAttributes redirectAttributes) {
		Long prodId = productDTO.getProdId();
		log.info("remove post..."+prodId);
		productService.remove(prodId);
		
		log.info(productDTO.getFileNames());
		List<String> fileNames = productDTO.getFileNames();
		if(fileNames != null && fileNames.size()>0) {
			removeFiles(fileNames);
		}
		redirectAttributes.addFlashAttribute("result", "removed");
		return "redirect:/product/list";
	}
	
	public void removeFiles(List<String> files) {
		for(String fileName : files) {
			Resource resource = new FileSystemResource(uploadPath + File.separator+fileName);
			String resourceName = resource.getFilename();
			
			try {
				String contentType = Files.probeContentType(resource.getFile().toPath());
				resource.getFile().delete();
				if(contentType.startsWith("image")) {
					File thumbnailFile = new File(uploadPath + File.separator+"thumb_"+fileName);
					thumbnailFile.delete();
				}
			} catch (IOException e) {
				log.error(e.getMessage());
				
			}
		}
	}
}
