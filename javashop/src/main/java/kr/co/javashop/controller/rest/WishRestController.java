package kr.co.javashop.controller.rest;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.co.javashop.dto.WishDTO;
import kr.co.javashop.service.WishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/wishrest/")
@Log4j2
@RequiredArgsConstructor
public class WishRestController {

	private final WishService wishService;
	
	@ApiOperation(value = "Wish Register", notes = "POST방식으로 찜목록 등록")
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Long> register(@Valid @RequestBody WishDTO wishDTO, BindingResult bidingResult) throws BindException {
		if(bidingResult.hasErrors()) {
			throw new BindException(bidingResult);
		}
		Long wid = wishService.register(wishDTO);
		Map<String, Long> resultMap = new HashMap<>();
		resultMap.put("wid", wid);
		return resultMap;		
	}

	@ApiOperation(value = "Wish Check", notes = "POST방식으로 찜목록 확인")
	@PostMapping(value = "/check", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Long> checkWishGET(@Valid @RequestBody WishDTO wishDTO, BindingResult bidingResult) {
		Long resultCode = wishService.checkWish(wishDTO.getMid(), wishDTO.getProdId());
		Map<String, Long> resultMap = new HashMap<>();
		resultMap.put("result", resultCode);
		return resultMap;
	}

	@ApiOperation(value = "Wish Remove", notes = "DELETE방식으로 찜목록 삭제")
	@DeleteMapping(value = "/{wid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Long> remove(@PathVariable("wid") Long wid) {
		wishService.remove(wid);
		Map<String, Long> resultMap = new HashMap<>();
		resultMap.put("result", 0L);
		return resultMap;		
	}

}
