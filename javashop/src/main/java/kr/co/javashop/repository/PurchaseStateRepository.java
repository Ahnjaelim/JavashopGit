package kr.co.javashop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.javashop.domain.PurchaseState;

public interface PurchaseStateRepository  extends JpaRepository<PurchaseState, Long>{

}
