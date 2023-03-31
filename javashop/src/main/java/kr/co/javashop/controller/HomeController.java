package kr.co.javashop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.javashop.service.CartService;
import kr.co.javashop.service.ProductService;
import kr.co.javashop.service.PurchaseService;
import kr.co.javashop.service.PurchaseStateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequiredArgsConstructor
public class HomeController {

	@GetMapping(value={"/",""})
	public String home() {
		return "redirect:/product/list";
	}
}
