package kr.co.javashop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import kr.co.javashop.domain.Cart;
import kr.co.javashop.repository.search.CartSearch;

public interface CartRepository extends JpaRepository<Cart, Long>, CartSearch {
	
	Optional<Cart> findByMemberIdAndProdId(@Param("memberId") String memberId, @Param("prodId") Long prodId);
	
	List<Cart> findAllByMemberIdOrderByCartIdDesc(@Param("memberId") String memberId);

}
