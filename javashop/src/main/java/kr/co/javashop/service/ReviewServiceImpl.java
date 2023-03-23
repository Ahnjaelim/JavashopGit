package kr.co.javashop.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import kr.co.javashop.domain.Review;
import kr.co.javashop.dto.PageRequestDTO;
import kr.co.javashop.dto.PageResponseDTO;
import kr.co.javashop.dto.ReviewDTO;
import kr.co.javashop.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewRepository;
	private final ModelMapper modelMapper;
	
	@Override
	public Long register(ReviewDTO reviewDTO) {
		Review review = modelMapper.map(reviewDTO, Review.class);
		Long revId = reviewRepository.save(review).getRevId();
		return revId;
	}

	@Override
	public ReviewDTO read(Long revId) {
		Optional<Review> reviewOptional = reviewRepository.findById(revId);
		Review review = reviewOptional.orElseThrow();
		return modelMapper.map(review, ReviewDTO.class);
	}

	@Override
	public void modify(ReviewDTO reviewDTO) {
		Optional<Review> reviewOptional = reviewRepository.findById(reviewDTO.getRevId());
		Review review = reviewOptional.orElseThrow();
		review.changeText(reviewDTO.getRevText());
		reviewRepository.save(review);
	}

	@Override
	public void remove(Long revId) {
		reviewRepository.deleteById(revId);
	}

	@Override
	public PageResponseDTO<ReviewDTO> getListOfBoard(Long prodId, PageRequestDTO pageRequestDTO) {
		Pageable pageable = PageRequest.of(pageRequestDTO.getPage() <= 0 ? 0 : pageRequestDTO.getPage() -1, pageRequestDTO.getSize(), Sort.by("revId").ascending());
		Page<Review> result = reviewRepository.listOfBoard(prodId, pageable);
		List<ReviewDTO> dtoList = result.getContent().stream().map(review -> modelMapper.map(review, ReviewDTO.class)).collect(Collectors.toList());
		return PageResponseDTO.<ReviewDTO>withAll()
				.pageRequestDTO(pageRequestDTO)
				.dtoList(dtoList)
				.total((int)result.getTotalElements())
				.build();
	}

}
