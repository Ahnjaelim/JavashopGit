package kr.co.javashop.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.javashop.dto.CartDTO;
import kr.co.javashop.dto.CartListDTO;
import kr.co.javashop.dto.ProductDTO;
import kr.co.javashop.dto.PurchaseDTO;
import kr.co.javashop.dto.PurchaseStateDTO;
import kr.co.javashop.security.dto.MemberSecurityDTO;
import kr.co.javashop.service.CartService;
import kr.co.javashop.service.ProductService;
import kr.co.javashop.service.PurchaseService;
import kr.co.javashop.service.PurchaseStateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

	private final CartService cartService;
	private final ProductService productService;
	private final PurchaseService purchaseService;
	private final PurchaseStateService purchaseStateService;
	
	@GetMapping("/username") 
    @ResponseBody 
    public String currentUserName(Principal principal) 
    { 
    	return principal.getName(); 
    } 	
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/list")
	public void list(Authentication authentication, Model model) {
		MemberSecurityDTO memberSecurityDTO = (MemberSecurityDTO) authentication.getPrincipal();
		log.info(memberSecurityDTO);
		List<CartListDTO> dtolist = cartService.getByMemberId(memberSecurityDTO.getMid());
		model.addAttribute("dtolist", dtolist);	
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/order")
	public void order(HttpServletRequest request, Model model) {
		List<Object> cartlist = new ArrayList<>();
		//Map<String, Object> cartlist = new HashMap<>();
		for(int i=0; i<request.getParameterValues("cartlist").length; i++) {
			System.out.println(request.getParameterValues("cartlist")[i]);
			Long cartId = Long.valueOf(request.getParameterValues("cartlist")[i]);
			CartDTO cartDTO = cartService.readOne(cartId);
			ProductDTO productDTO = productService.readOne(cartDTO.getProdId());
			CartListDTO cartListDTO = CartListDTO.builder()
					.cartId(cartId)
					.prodId(productDTO.getProdId())
					.prodCnt(cartDTO.getProdCnt())
					.prodName(productDTO.getProdName())
					.prodPrice(productDTO.getProdPrice())
					.build();
			cartlist.add(cartListDTO);
		}
		model.addAttribute("cartlist", cartlist);
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/orderOk")
	public void orderOk(Authentication authentication, HttpServletRequest request, Model model) {
		MemberSecurityDTO memberSecurityDTO = (MemberSecurityDTO) authentication.getPrincipal();
		String purNo = String.valueOf(System.currentTimeMillis());
		int totalPrice = 0;
		for(int i=0; i<request.getParameterValues("cartlist").length; i++) {
			System.out.println(request.getParameterValues("cartlist")[i]);
			Long cardId = Long.valueOf(request.getParameterValues("cartlist")[i]);
			CartDTO cartDTO = cartService.readOne(cardId);
			ProductDTO productDTO = productService.readOne(cartDTO.getProdId());
			int purTotalprice = cartDTO.getProdCnt()*productDTO.getProdPrice();
			PurchaseDTO purchaseDTO = PurchaseDTO.builder()
					.purNo(purNo)
					.prodId(cartDTO.getProdId())
					.prodCnt(cartDTO.getProdCnt())
					.purTotalprice(purTotalprice)
					.purDelivery(0)
					.purState(0)
					.memberId(memberSecurityDTO.getMid())
					.build();
			purchaseService.register(purchaseDTO);
			totalPrice = totalPrice + purTotalprice;
			cartService.remove(cardId);
		}
		PurchaseStateDTO purchaseStateDTO = PurchaseStateDTO.builder()
				.purNo(purNo)
				.memberId(memberSecurityDTO.getMid())
				.purState(0)
				.purTotalprice(totalPrice)
				.build();
		purchaseStateService.register(purchaseStateDTO);
		
	}
	
}
