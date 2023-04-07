package kr.co.javashop.service;

import java.util.List;

import kr.co.javashop.domain.Category;
import kr.co.javashop.dto.CategoryDTO;

public interface CategoryService {

	Long register(CategoryDTO categoryDTO);
	
	List<CategoryDTO> getAll(Long depth);

	/*
	default Category dtoToEntity(CategoryDTO categoryDTO) {
		Category category = Category.builder()
			.cateDepth(categoryDTO.getCateDepth())
			.cateName(categoryDTO.getCateName())
			.build();
		return category;
	}*/
}
