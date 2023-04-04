package kr.co.javashop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.javashop.domain.Category;

public interface CategoryRepository  extends JpaRepository<Category, String>{
	
	List<Category> findAllByCateDepth(Long cateDepth);
}
