package kr.co.javashop.service;

import kr.co.javashop.dto.PageRequestDTO;
import kr.co.javashop.dto.PageResponseDTO;
import kr.co.javashop.dto.ReviewDTO;

public interface ReviewService {
	Long register(ReviewDTO reviewDTO);
	
	ReviewDTO read(Long revId);
	
	void modify(ReviewDTO reviewDTO);
	
	void remove(Long revId);
	
	PageResponseDTO<ReviewDTO> getListOfBoard(Long prodId, PageRequestDTO pageRequestDTO);
}
