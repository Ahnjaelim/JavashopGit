package kr.co.javashop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import kr.co.javashop.domain.Wish;
import kr.co.javashop.repository.search.WishSearch;

public interface WishRepository extends JpaRepository<Wish, Long>, WishSearch {

	Optional<Wish> findByMidAndProdId(@Param("mid") String mid, @Param("prodId") Long prodId); 
	
	List<Wish> findAllByMid(@Param("mid") String mid);
}
