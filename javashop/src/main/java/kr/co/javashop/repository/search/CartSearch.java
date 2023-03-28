package kr.co.javashop.repository.search;

import java.util.List;

import kr.co.javashop.dto.CartListDTO;

public interface CartSearch {

	List<CartListDTO> findByMemberId(String memberId);
}
