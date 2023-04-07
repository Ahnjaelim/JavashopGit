package kr.co.javashop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import kr.co.javashop.domain.Category;

public interface CategoryRepository  extends JpaRepository<Category, String>{
	
	List<Category> findAllByCateDepth(Long cateDepth);
	
	@Modifying
	@Transactional
	@Query(value = "insert into category (catename, catedepth, cateparent) values (?, ?, ?)", nativeQuery = true)
	void save(@Param("catename") Object cateName, @Param("catedepth") Object cateDepth, @Param("cateparent") Object cateParent);
}
