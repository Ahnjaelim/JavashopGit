package kr.co.javashop.service;

import java.util.List;

import kr.co.javashop.dto.CartDTO;
import kr.co.javashop.dto.CartListDTO;

public interface CartService {

	Long register(CartDTO cartDTO);
	
	CartDTO cartCheck(CartDTO cartDTO);
	
	CartDTO readOne(Long cartId);
	
	void addProdCnt(CartDTO cartDTO);
	void modifyCnt(CartDTO cartDTO);
	
	List<CartListDTO> getByMemberId(String memberId);
	
	void remove(Long cartId);
}
