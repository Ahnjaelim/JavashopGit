package kr.co.javashop.service;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.javashop.dto.PageRequestDTO;
import kr.co.javashop.dto.PageResponseDTO;
import kr.co.javashop.dto.ProductDTO;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    // @Test
    public void testRegister(){
        log.info(productService.getClass().getName());
        ProductDTO productDTO = ProductDTO.builder()
                .cateCode("0002")
                .prodName("Product Name Service Test")
                .prodPrice(5000)
                .prodStock(100)
                .prodDesc("Product Desc Service Test")
                .prodHit(0)
                .build();
        Long prodId = productService.register(productDTO);
        log.info("Product ID : "+prodId);
    }

    // @Test
    public void modifyTest(){
        ProductDTO productDTO = ProductDTO.builder()
                .prodId(101L)
                .cateCode("0002")
                .prodName("Product Modify Service Test")
                .prodDesc("Product Modify Service Test")
                .prodStock(30)
                .prodPrice(1000)
                .build();
        productService.modify(productDTO);
    }
    
    @Test
    public void testList() {
    	PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
    			.type("prodName")
    			.keyword("1")
    			.page(1)
    			.size(10)
    			.build();
    	PageResponseDTO<ProductDTO> responseDTO = productService.list(pageRequestDTO);
    	log.info(responseDTO);
    }
}
