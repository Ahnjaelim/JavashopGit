package kr.co.javashop.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.javashop.dto.ReviewDTO;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ReviewServiceTest {

	@Autowired
	private ReviewService reviewService;
	
	@Test
	public void testRegister() {
		ReviewDTO reviewDTO = ReviewDTO.builder()
				.revName("작성자")
				.revText("댓글 등록서비스단 테스트")
				.prodId(100L)
				.build();
		log.info(reviewService.register(reviewDTO));
	}
}
