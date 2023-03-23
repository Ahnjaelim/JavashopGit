package kr.co.javashop.service;

import kr.co.javashop.dto.PageRequestDTO;
import kr.co.javashop.dto.PageResponseDTO;
import kr.co.javashop.dto.ProductDTO;
import kr.co.javashop.dto.ProductListReviewCountDTO;

public interface ProductService {

    Long register(ProductDTO productDTO);

    ProductDTO readOne(Long prodId);

    void modify(ProductDTO productDTO);
    
    void remove(Long prodId);
    
    PageResponseDTO<ProductDTO> list(PageRequestDTO pageRequestDTO);
    
    // 목록에서 댓글 수까지 처리
    PageResponseDTO<ProductListReviewCountDTO> listWithReviewCount(PageRequestDTO pageRequestDTO);
}