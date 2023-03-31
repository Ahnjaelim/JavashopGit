package kr.co.javashop.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.javashop.security.dto.MemberSecurityDTO;
import kr.co.javashop.service.WishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/wish")
@RequiredArgsConstructor
public class WishController {

	private final WishService wishService;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/list")
	public void listGET(Model model, Authentication authentication) {
		MemberSecurityDTO memberSecurityDTO = (MemberSecurityDTO) authentication.getPrincipal();
		model.addAttribute("dtolist", wishService.getAll(memberSecurityDTO.getMid()));
		
		
	}
}
