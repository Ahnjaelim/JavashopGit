package kr.co.javashop.service;

import java.util.List;

import kr.co.javashop.dto.PurchaseStateDTO;

public interface PurchaseStateService {

	String register(PurchaseStateDTO purchaseStateDTO);
	
	List<PurchaseStateDTO> getAll();
	List<PurchaseStateDTO> getAllByMemberId(String memberId);
}
