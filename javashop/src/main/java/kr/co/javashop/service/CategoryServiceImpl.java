package kr.co.javashop.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import kr.co.javashop.domain.Category;
import kr.co.javashop.dto.CategoryDTO;
import kr.co.javashop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	
	private final ModelMapper modelMapper;
	private final CategoryRepository categoryRepository;
	
	@Override
	public Long register(CategoryDTO categoryDTO) {
		System.out.println(categoryDTO);
		// Category category =  modelMapper.map(categoryDTO, Category.class);
		Category category = dtoToEntity(categoryDTO);
		System.out.println(category);
		Long cateCode = categoryRepository.save(category).getCateCode();
		return cateCode;
	}

	@Override
	public List<CategoryDTO> getAll(Long depth) {
		List<CategoryDTO> dtolist = null;
		if(depth != null) {
			dtolist = categoryRepository.findAllByCateDepth(depth).stream().map(entity -> modelMapper.map(entity, CategoryDTO.class)).collect(Collectors.toList());
		}else {
			dtolist = categoryRepository.findAll().stream().map(entity -> modelMapper.map(entity, CategoryDTO.class)).collect(Collectors.toList());
		}
		return dtolist;
	}

}
