package kr.co.javashop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.javashop.domain.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

	List<Purchase> findAllByPurNo(String purNo);
}
