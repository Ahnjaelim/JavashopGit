package kr.co.javashop.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.javashop.domain.Category;
import kr.co.javashop.dto.CategoryDTO;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class CategoryServiceTest {

	@Autowired CategoryService categoryService;
	
	@Test
	public void registerTest() {
		CategoryDTO categoryDTO = CategoryDTO.builder()
			.cateName("목도리")
			.cateDepth(1L)
			.cateParentCode(null)
			.build();
		System.out.println(categoryDTO);
		Long cateCode = categoryService.register(categoryDTO);
		System.out.println(cateCode);
	}
}
