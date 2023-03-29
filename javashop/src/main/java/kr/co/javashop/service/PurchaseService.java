package kr.co.javashop.service;

import java.util.List;

import kr.co.javashop.dto.PurchaseDTO;

public interface PurchaseService {

	Long register(PurchaseDTO purchaseDTO);
	
	List<PurchaseDTO> getAll();
	List<PurchaseDTO> getAllByPurNo(String purNo);
}
