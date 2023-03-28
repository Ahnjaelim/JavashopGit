package kr.co.javashop.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.javashop.domain.Cart;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class CartRepositoryTest {

	@Autowired
	private CartRepository cartRepository;
	
	// @Test
	public void findByMemberIdAndProdIdTest() {
		Optional<Cart> result = cartRepository.findByMemberIdAndProdId("user00", 2L);
		Cart cart = result.orElseThrow();
		log.info(cart);
	}
	
	@Test
	public void findAllByMemberIdTest() {
		List<Cart> entitylist = cartRepository.findAllByMemberIdOrderByCartIdDesc("user01");
		entitylist.forEach(entity -> System.out.println(entity));
	}
}
