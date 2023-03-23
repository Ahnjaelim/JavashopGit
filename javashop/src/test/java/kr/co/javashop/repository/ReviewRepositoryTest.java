package kr.co.javashop.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import kr.co.javashop.domain.Product;
import kr.co.javashop.domain.Review;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ReviewRepositoryTest {

	@Autowired
	private ReviewRepository reviewRepository;
	
	// @Test
	public void reviewInsertTest() {
		
		// 실제 DB에 있는 prodId
		Long prodId = 100L;
		Product product = Product.builder().prodId(prodId).build();
		Review review = Review.builder()
				.product(product)
				.revName("작성자")
				.revText("댓글 레포지토리 테스트")
				.build();
		reviewRepository.save(review);
	}
	
	@Test
	public void testBoardReviewTest() {
		Long prodId = 100L;
		Pageable pageable = PageRequest.of(0, 10, Sort.by("revId").descending());
		Page<Review> result = reviewRepository.listOfBoard(prodId, pageable);
		result.getContent().forEach(reply -> {
			log.info(reply);
		});
	}
}
