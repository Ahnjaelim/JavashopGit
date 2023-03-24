package kr.co.javashop.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import kr.co.javashop.domain.Product;
import kr.co.javashop.dto.ProductListAllDTO;
import kr.co.javashop.dto.ProductListReviewCountDTO;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private ReviewRepository reviewRepository;

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
    
    // @Test
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
    
    // @Test
    @DisplayName("상품+상품이미지 insert 테스트")
    public void testInsertWithImage() {
    	Product product = Product.builder().cateCode("0001")
    			.prodName("Image Test")
    			.prodDesc("첨부파일 테스트").prodPrice(1000).prodStock(1000).build();
    	for(int i=0; i<3; i++) {
    		product.addImage(UUID.randomUUID().toString(), "file"+i+".jpg");
    	}
    	
    	productRepository.save(product);
    			
    }
    
    @Test
    @DisplayName("게시글을 이미지와 함께 조회")
    public void tesReadWithImages() {
    	// Optional<Product> result = productRepository.findById(1L); => 기존에 있던 findById로 조회하면 오류!
    	Optional<Product> result = productRepository.findByIdWithImages(1L);
    	Product product = result.orElseThrow();
    	
    	log.info(product);
    	log.info("------------------------------");
    	log.info(product.getImageSet());
    	
    }
    
    // @Test
    @DisplayName("게시물과 첨부파일 수정")
    public void testModifyImages() {
    	Optional<Product> result = productRepository.findByIdWithImages(1L);
    	Product product = result.orElseThrow();
    	product.clearImages();
    	for(int i = 0; i < 2; i++) {
    		product.addImage(UUID.randomUUID().toString(), "updatefile"+i+".jpg");
    	}
    	productRepository.save(product);
    	
    }
    
    // @Test
    @DisplayName("게시물과 첨부파일, 댓글 삭제")
    public void testRemoveAll() {
    	Long prodId = 1L;
    	reviewRepository.deleteByProduct_ProdId(prodId);
    	productRepository.deleteById(prodId);
    }
    
    // @Test
    @DisplayName("테스트를 위한 더미데이터 삽입")
    public void testInsertAll() {
    	for(int i = 1; i <= 100; i++) {
    		Product product = Product.builder()
    				.cateCode("0001")
    				.prodName("insert 테스트")
    				.prodDesc("insert 테스트 desc")
    				.prodPrice(1000)
    				.prodStock(1000)
    				.build();
    		for (int j = 0; j < 3; j++) {
    			if(i % 5 == 0) {
    				continue;
    			}
    			product.addImage(UUID.randomUUID().toString(), i+"file"+j+".jpg");
    		}
    		productRepository.save(product);
    	}
    }
    
    // @Transactional
    // @Test
    @DisplayName("목록 데이터 처리")
    public void testSearchImageReviewCount() {
    	Pageable pageable = PageRequest.of(0, 10, Sort.by("prodId").descending());
    	productRepository.searchWithAll1(null, null, null, null, pageable);
    }
    
    @Transactional
    @Test
    @DisplayName("목록 데이터 처리")
    public void testSearchImageReviewCount2() {
    	Pageable pageable = PageRequest.of(0, 10, Sort.by("prodId").descending());
    	Page<ProductListAllDTO> result = productRepository.searchWithAll(null, null, null, null, pageable);
    	log.info("------------------------------");
    	log.info(result.getTotalElements());
    	result.getContent().forEach(productListAllDTO -> log.info(productListAllDTO));
    }    
}
