package kr.co.javashop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.javashop.domain.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

}
