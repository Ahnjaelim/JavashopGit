package kr.co.javashop.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.co.javashop.dto.PageRequestDTO;
import kr.co.javashop.dto.PageResponseDTO;
import kr.co.javashop.dto.ReviewDTO;
import kr.co.javashop.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/review")
@Log4j2
@RequiredArgsConstructor
public class ReviewController {
	
	private final ReviewService reviewService; 
	
	@ApiOperation(value = "Review POST", notes = "POST 방식으로 댓글 등록") // 스웨거에서 해당 기능 설명
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE) // JSON 타입의 데이터를 소비
	public Map<String, Long> register(@Valid @RequestBody ReviewDTO reviewDTO, BindingResult bidingResult) throws BindException { // @RequestBody -> JSON 문자열을 DTO로 변환
		log.info(reviewDTO);
		if(bidingResult.hasErrors()) {
			throw new BindException(bidingResult);
		}
		Map<String, Long> resultMap = new HashMap<>();
		Long revId = reviewService.register(reviewDTO);
		resultMap.put("revId", revId);
		return resultMap;
	}
	
	@ApiOperation(value = "Reviews of Product", notes = "GET 방식으로 특정게시물의 댓글 목록 조회")
	@GetMapping(value = "/list/{prodId}")
	public PageResponseDTO<ReviewDTO> getList(@PathVariable("prodId") Long prodId, PageRequestDTO pageRequestDTO){
		PageResponseDTO<ReviewDTO> responseDTO = reviewService.getListOfBoard(prodId, pageRequestDTO);
		return responseDTO;
	}
	
	@ApiOperation(value = "Delete Reply", notes = "DELETE 방식으로 특정 댓글 삭제")
	@DeleteMapping("/{revId}")
	public Map<String, Long> remove(@PathVariable("revId") Long revId){
		reviewService.remove(revId);
		Map<String, Long> resultMap = new HashMap<>();
		resultMap.put("revId", revId);
		return resultMap;
	}
	
	@ApiOperation(value = "Modify Reply", notes = "PUT 방식으로 특정 댓글 수정")
	@PutMapping(value = "/{revId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Long> modify(@PathVariable("revId") Long revId, @RequestBody ReviewDTO reviewDTO){
		reviewDTO.setRevId(revId); // 번호를 일치시킴
		reviewService.modify(reviewDTO);
		Map<String, Long> resultMap = new HashMap<>();
		resultMap.put("revId", revId);
		return resultMap;
	}
}
