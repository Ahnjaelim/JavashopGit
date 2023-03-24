package kr.co.javashop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.javashop.dto.PageRequestDTO;
import kr.co.javashop.dto.PageResponseDTO;
import kr.co.javashop.dto.ProductDTO;
import kr.co.javashop.dto.ProductImageDTO;
import kr.co.javashop.dto.ProductListAllDTO;
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
    
    // @Test
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
    
    // @Test
    public void testRegisterWithImages() {
    	log.info(productService.getClass().getName());
    	ProductDTO productDTO = ProductDTO.builder()
    			.cateCode("0002")
    			.prodName("File...Sample Title...")
    			.prodDesc("File...Sample Title...Desc")
    			.prodPrice(1000)
    			.prodStock(1000)
    			.build();
		List<String> templist = new ArrayList<>();
		templist.add(UUID.randomUUID()+"_aaa.jpg");
		templist.add(UUID.randomUUID()+"_bbb.jpg");
		templist.add(UUID.randomUUID()+"_ccc.jpg");
		productDTO.setFileNames(templist);
		Long prodId = productService.register(productDTO);
		log.info("prodId : "+prodId);
    }
    
    // @Test
    public void testReadAll() {
    	Long prodId = 101L;
    	ProductDTO productDTO = productService.readOne(prodId);
    	log.info(productDTO);
    	for(String fineName : productDTO.getFileNames()) {
    		log.info(fineName);
    	}
    }
    
    // @Test
    public void testModify() {
    	ProductDTO productDTO = ProductDTO.builder()
    			.prodId(101L)
    			.cateCode("0002")
    			.prodName("Modify Test - Name")
    			.prodDesc("Modify Test - Desc")
    			.prodPrice(1000)
    			.prodStock(1000)
    			.build();
		List<String> templist = new ArrayList<>();
		templist.add(UUID.randomUUID()+"_zzz.jpg");
    	productDTO.setFileNames(templist);
    	productService.modify(productDTO);
    }
    
    // @Test
    public void testRemoveAll() {
    	Long prodId = 1L;
    	productService.remove(prodId);
    	
    }
    
    @Test
    public void testListWithAll() {
    	PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
    			.page(1)
    			.size(10)
    			.build();
    	PageResponseDTO<ProductListAllDTO> responseDTO = productService.listWithAll(pageRequestDTO);
    	List<ProductListAllDTO> dtoList = responseDTO.getDtoList();
    	dtoList.forEach(productListAllDTO -> {
    		log.info(productListAllDTO.getProdId()+" : "+productListAllDTO.getProdName());
    		if(productListAllDTO.getProductImages()!=null) {
    			for(ProductImageDTO productImage : productListAllDTO.getProductImages()) {
    				log.info(productImage);
    			}
    		}
    		
    		log.info("--------------------------------------------------");
    		
    	});
    }
}
