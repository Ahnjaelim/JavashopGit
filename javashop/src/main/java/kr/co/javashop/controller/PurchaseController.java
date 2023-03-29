package kr.co.javashop.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.javashop.security.dto.MemberSecurityDTO;
import kr.co.javashop.service.PurchaseService;
import kr.co.javashop.service.PurchaseStateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/purchase")
@RequiredArgsConstructor
public class PurchaseController {

	private final PurchaseService purchaseService;
	private final PurchaseStateService purchaseStateService;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/list")
	public void list(Model model, Authentication authentication) {
		MemberSecurityDTO memberSecurityDTO = (MemberSecurityDTO) authentication.getPrincipal();
		model.addAttribute("dtolist", purchaseStateService.getAllByMemberId(memberSecurityDTO.getMid()));
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/read")
	public void read(Model model, Authentication authentication, String purNo) {
		MemberSecurityDTO memberSecurityDTO = (MemberSecurityDTO) authentication.getPrincipal();
		model.addAttribute("dtolist", purchaseService.getAllByPurNo(purNo));
		
	}	
}
