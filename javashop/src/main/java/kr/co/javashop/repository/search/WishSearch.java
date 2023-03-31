package kr.co.javashop.repository.search;

import java.util.List;

import kr.co.javashop.dto.WishListDTO;

public interface WishSearch {

	List<WishListDTO> findAllByMidJoin(String mid);
}
