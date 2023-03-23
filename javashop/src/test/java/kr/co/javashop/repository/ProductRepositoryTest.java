package kr.co.javashop.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import kr.co.javashop.domain.Product;
import kr.co.javashop.dto.ProductListReviewCountDTO;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    // @Test
    public void testInsert(){
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Product product = Product.builder()
                    .prodName("Product "+i)
                    .cateCode("0001")
                    .prodDesc(i+"번 상품에 대한 설명입니다.")
                    .prodPrice(10000)
                    .prodStock(100)
                    .build();
            Product result = productRepository.save(product);
            log.info("Product ID : "+result.getProdId());
        });
    }

    // @Test
    public void testSelect(){ // 레코드 1개 조회
        Long prodId = 100L;
        Optional<Product> result = productRepository.findById(prodId);
        Product product = result.orElseThrow();
        log.info(product);
    }

    // @Test
    public void testUpdate(){
        Long prodId = 100L;
        Optional<Product> result = productRepository.findById(prodId); // 해당 데이터 조회
        Product product = result.orElseThrow();
        product.change("Updated Product Name", "0002", 11000, 110, "수정된 상품 설명입니다.");
        productRepository.save(product);
    }

    // @Test
    public void testDelete(){
        Long prodId = 1L;
        productRepository.deleteById(prodId);
    }

    // @Test
    public void testPageing(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("prodId").descending());
        Page<Product> result = productRepository.findAll(pageable);
        log.info("total count : "+result.getTotalElements());
        log.info("total pages : "+result.getTotalPages());
        log.info("page number : "+result.getNumber());
        log.info("page size : "+result.getSize());
        List<Product> dtolist = result.getContent();
        dtolist.forEach(dto -> log.info(dto));
    }

    // @Test
    public void testSearch(){
        String[] types = {"prodName","prodDesc"};
        String keyword = "1";
        String category = "0001";
        String[] states = {"0", "1"};
        Pageable pageable = PageRequest.of(0, 10, Sort.by("prodId").descending());
        Page<Product> result = productRepository.searchAll(types, keyword, category, states, pageable);
        log.info(result.getTotalPages());
        log.info(result.getSize());
        log.info(result.getNumber());
        log.info(result.hasPrevious()+" : "+result.hasNext());
        result.getContent().forEach(product -> log.info(product));
    }
    
    @Test
    public void testSearchReviewTest() {
        String[] types = {"prodName","prodDesc"};
        String keyword = "0";
        String category = "0001";
        String[] states = {"0", "1"};
        Pageable pageable = PageRequest.of(0, 10, Sort.by("prodId").descending());
        Page<ProductListReviewCountDTO> result = productRepository.searchWithReviewCount(types, keyword, category, states, pageable);
        log.info(result.getTotalPages());
        log.info(result.getSize());
        log.info(result.getNumber());
        log.info(result.hasPrevious()+" : "+result.hasNext());
        result.getContent().forEach(product -> log.info(product));
    }
}
