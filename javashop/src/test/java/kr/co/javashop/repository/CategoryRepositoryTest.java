package kr.co.javashop.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.javashop.domain.Category;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class CategoryRepositoryTest {

	@Autowired
	private CategoryRepository categoryRepository;
	
	// @Test
	public void findAllByCateDepthTest() {
		List<Category> category = categoryRepository.findAllByCateDepth(1L);
		category.forEach(entity -> System.out.println(entity));
	}
	
	@Test
	public void saveTest() {
		categoryRepository.save("레포지토리 테스트", 2L, 1L);
	}
}
