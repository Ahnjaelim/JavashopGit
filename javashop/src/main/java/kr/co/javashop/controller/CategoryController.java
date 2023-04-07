package kr.co.javashop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.javashop.dto.CategoryDTO;
import kr.co.javashop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
	
	private final CategoryService categoryService;

	@GetMapping("/list")
	public void listGET(Model model) {
		model.addAttribute("dtolist", categoryService.getAll(1L));
		
	}
	
	@GetMapping("/register")
	public void registerGET(Model model) {
		model.addAttribute("catelist", categoryService.getAll(1L));
		
	}

	@PostMapping("/register")
	public String registerPOST(CategoryDTO categoryDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		log.info(categoryDTO);
		if(bindingResult.hasErrors()) {
			log.info("has error!");
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			return "redirect:/category/register";
		}
		
		categoryService.register(categoryDTO);
		// redirectAttributes.addFlashAttribute("result", cateCode);
		return "redirect:/category/list";
	}	
	
}
