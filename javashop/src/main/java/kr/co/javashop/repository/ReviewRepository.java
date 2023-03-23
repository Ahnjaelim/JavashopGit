package kr.co.javashop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.co.javashop.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{

	@Query("select r from Review r where r.product.prodId = :prodId")
	Page<Review> listOfBoard(@Param("prodId") Long prodId, @Param("pageable") Pageable pageable);
}
