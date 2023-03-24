package kr.co.javashop.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import kr.co.javashop.domain.Product;
import kr.co.javashop.dto.PageRequestDTO;
import kr.co.javashop.dto.PageResponseDTO;
import kr.co.javashop.dto.ProductDTO;
import kr.co.javashop.dto.ProductListAllDTO;
import kr.co.javashop.dto.ProductListReviewCountDTO;

public interface ProductService {

    Long register(ProductDTO productDTO);

    ProductDTO readOne(Long prodId);

    void modify(ProductDTO productDTO);
    
    void remove(Long prodId);
    
    PageResponseDTO<ProductDTO> list(PageRequestDTO pageRequestDTO);
    
    // 목록에서 댓글 수까지 처리
    PageResponseDTO<ProductListReviewCountDTO> listWithReviewCount(PageRequestDTO pageRequestDTO);
    
    // 목록에서 댓글, 이미지까지 처리
    Page<ProductListAllDTO> searchWithAll(PageRequestDTO pageRequestDTO);
    
    // 게시글의 이미지와 댓글의 숫자까지 처리
    PageResponseDTO<ProductListAllDTO> listWithAll(PageRequestDTO pageRequestDTO);

    default Product dtoToEntity(ProductDTO productDTO) {
    	Product product = Product.builder()
    		.prodId(productDTO.getProdId())
    		.cateCode(productDTO.getCateCode())
    		.prodName(productDTO.getProdName())
    		.prodDesc(productDTO.getProdDesc())
    		.prodPrice(productDTO.getProdPrice())
    		.prodStock(productDTO.getProdStock())
    		.build();
    	if(productDTO.getFileNames()!=null) {
    		productDTO.getFileNames().forEach(fileName -> {
    			String[] arr = fileName.split("_");
    			product.addImage(arr[0], arr[1]);
    		});
    	}
    	return product;
    		
    }
    
    default ProductDTO entityToDTO(Product product) {
    	ProductDTO productDTO = ProductDTO.builder()
        		.prodId(product.getProdId())
        		.cateCode(product.getCateCode())
        		.prodName(product.getProdName())
        		.prodDesc(product.getProdDesc())
        		.prodPrice(product.getProdPrice())
        		.prodStock(product.getProdStock())
        		.build();
    	List<String> fileNames = product.getImageSet().stream().sorted().map(productImage -> 
    		productImage.getUuid()+"_"+productImage.getFileName()
    	).collect(Collectors.toList());
    	productDTO.setFileNames(fileNames);
    	return productDTO;
    }
    
}