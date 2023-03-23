package kr.co.javashop.controller;

import javax.validation.Valid;

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
import kr.co.javashop.dto.ProductListReviewCountDTO;
import kr.co.javashop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
	
	private final ProductService productService;
	
	@GetMapping("/list")
	public void list(PageRequestDTO pageRequestDTO, Model model) {
		log.info("<Product Controller> list GET");
		// PageResponseDTO<ProductDTO> responseDTO = productService.list(pageRequestDTO);
		PageResponseDTO<ProductListReviewCountDTO> responseDTO = productService.listWithReviewCount(pageRequestDTO);
		log.info(responseDTO);
		model.addAttribute("responseDTO", responseDTO);
	}
	
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
		log.info("<Product Controller> read GET");
		ProductDTO productDTO = productService.readOne(prodId);
		log.info(productDTO);
		model.addAttribute("dto", productDTO);
		
	}
}
