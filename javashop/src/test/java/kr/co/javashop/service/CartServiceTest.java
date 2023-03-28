package kr.co.javashop.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.javashop.dto.CartDTO;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class CartServiceTest {

	@Autowired
	private CartService cartService;
	
	@Test
	public void cartCheckTest() {

	}
}
