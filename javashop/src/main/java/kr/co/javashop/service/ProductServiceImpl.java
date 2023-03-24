package kr.co.javashop.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.co.javashop.domain.Product;
import kr.co.javashop.dto.PageRequestDTO;
import kr.co.javashop.dto.PageResponseDTO;
import kr.co.javashop.dto.ProductDTO;
import kr.co.javashop.dto.ProductListAllDTO;
import kr.co.javashop.dto.ProductListReviewCountDTO;
import kr.co.javashop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    @Override
    public Long register(ProductDTO productDTO) {
        // Product product = modelMapper.map(productDTO, Product.class);
    	Product product = dtoToEntity(productDTO);
        Long prodId = productRepository.save(product).getProdId();
        return prodId;
    }

    @Override
    public ProductDTO readOne(Long prodId) {
        // Optional<Product> result = productRepository.findById(prodId);
        Optional<Product> result = productRepository.findByIdWithImages(prodId);
        Product product = result.orElseThrow();
        // ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        ProductDTO productDTO = entityToDTO(product);
        return productDTO;
    }

    @Override
    public void modify(ProductDTO productDTO) {
        Optional<Product> result = productRepository.findById(productDTO.getProdId());
        Product product = result.orElseThrow();
        product.change(productDTO.getProdName(), productDTO.getCateCode(), productDTO.getProdPrice(), productDTO.getProdStock(), productDTO.getProdDesc());
        
        // 첨부파일 처리
        product.clearImages();
        if(productDTO.getFileNames()!=null) {
        	for(String fileName : productDTO.getFileNames()) {
        		String[] arr = fileName.split("_");
        		product.addImage(arr[0], arr[1]);
        	}
        }
        
        productRepository.save(product);
    }

	@Override
	public void remove(Long prodId) {
		productRepository.deleteById(prodId);
		
	}

	@Override
	public PageResponseDTO<ProductDTO> list(PageRequestDTO pageRequestDTO) {
		log.info(pageRequestDTO);
		// String[] types = pageRequestDTO.getTypes();
		String[] types = null;
		if(pageRequestDTO.getType()!=null) {
			types = pageRequestDTO.getType().split(",");
		}
		String keyword = pageRequestDTO.getKeyword();
		String category = pageRequestDTO.getCategory();
		String[] states = {};
		Pageable pageable = pageRequestDTO.getPageable("prodId");
		Page<Product> result = productRepository.searchAll(types, keyword, category, states, pageable);
		
		List<ProductDTO> dtoList = result.getContent().stream()
				.map(product -> modelMapper.map(product, ProductDTO.class)).collect(Collectors.toList());
		return PageResponseDTO.<ProductDTO>withAll()
				.pageRequestDTO(pageRequestDTO)
				.dtoList(dtoList)
				.total((int)result.getTotalElements())
				.build();
	}

	@Override
	public PageResponseDTO<ProductListReviewCountDTO> listWithReviewCount(PageRequestDTO pageRequestDTO) {
		log.info(pageRequestDTO);
		String[] types = null;
		if(pageRequestDTO.getType()!=null) {
			types = pageRequestDTO.getType().split(",");
		}
		String keyword = pageRequestDTO.getKeyword();
		String category = pageRequestDTO.getCategory();
		String[] states = {};
		Pageable pageable = pageRequestDTO.getPageable("prodId");
		Page<ProductListReviewCountDTO> result = productRepository.searchWithReviewCount(types, keyword, category, states, pageable);
		return PageResponseDTO.<ProductListReviewCountDTO>withAll()
				.pageRequestDTO(pageRequestDTO)
				.dtoList(result.getContent())
				.total((int)result.getTotalElements())
				.build();
	}

	@Override
	public Page<ProductListAllDTO> searchWithAll(PageRequestDTO pageRequestDTO) {
		return null;
	}

	@Override
	public PageResponseDTO<ProductListAllDTO> listWithAll(PageRequestDTO pageRequestDTO) {
		log.info(pageRequestDTO);
		String[] types = null;
		if(pageRequestDTO.getType()!=null) {
			types = pageRequestDTO.getType().split(",");
		}
		String keyword = pageRequestDTO.getKeyword();
		String category = pageRequestDTO.getCategory();
		String[] states = {};
		Pageable pageable = pageRequestDTO.getPageable("prodId");
		Page<ProductListAllDTO> result = productRepository.searchWithAll(types, keyword, category, states, pageable);
		return PageResponseDTO.<ProductListAllDTO>withAll()
				.pageRequestDTO(pageRequestDTO)
				.dtoList(result.getContent())
				.total((int)result.getTotalElements())
				.build();
	}
}