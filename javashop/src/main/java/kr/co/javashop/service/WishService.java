package kr.co.javashop.service;

import java.util.List;

import kr.co.javashop.dto.WishDTO;
import kr.co.javashop.dto.WishListDTO;

public interface WishService {
	
	Long register(WishDTO wishDTO);

	WishDTO readOne(String mid, Long prodId);
	
	Long checkWish(String mid, Long prodId);
	
	void remove(String mid, Long prodId);
	void remove(Long wid);
	
	List<WishListDTO> getAll(String mid);
}
