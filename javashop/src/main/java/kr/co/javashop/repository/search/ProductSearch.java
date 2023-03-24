package kr.co.javashop.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.javashop.domain.Product;
import kr.co.javashop.dto.ProductListAllDTO;
import kr.co.javashop.dto.ProductListReviewCountDTO;

public interface ProductSearch {

    Page<Product> search(Pageable pageable);

    Page<Product> searchAll(String[] types, String keyword, String category, String[] states, Pageable pageable);
    
    // 리뷰 갯수 추가
    Page<ProductListReviewCountDTO> searchWithReviewCount(String[] types, String keyword, String category, String[] states, Pageable pageable);
    
    // 이미지 추가
    Page<ProductListAllDTO> searchWithAll1(String[] types, String keyword, String category, String[] states, Pageable pageable);
    Page<ProductListAllDTO> searchWithAll(String[] types, String keyword, String category, String[] states, Pageable pageable);
}
