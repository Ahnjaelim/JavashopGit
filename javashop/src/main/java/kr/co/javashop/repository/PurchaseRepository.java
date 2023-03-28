package kr.co.javashop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.javashop.domain.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

}
