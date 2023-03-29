package kr.co.javashop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.javashop.domain.PurchaseState;

public interface PurchaseStateRepository  extends JpaRepository<PurchaseState, String>{
	
	List<PurchaseState> findAllByMemberId(String memberId);

}
