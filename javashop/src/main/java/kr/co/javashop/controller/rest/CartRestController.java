package kr.co.javashop.controller.rest;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.co.javashop.dto.CartDTO;
import kr.co.javashop.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/cartrest")
@Log4j2
@RequiredArgsConstructor
public class CartRestController {

	private final CartService cartService;
	
	@ApiOperation(value = "Cart Register", notes = "POST방식으로 카트 입력")
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Long> register(@Valid @RequestBody CartDTO cartDTO, BindingResult bidingResult) throws BindException {
		log.info(cartDTO);
		if(bidingResult.hasErrors()) {
			throw new BindException(bidingResult);
		}
		Map<String, Long> resultMap = new HashMap<>();
		Long cartId = cartService.register(cartDTO);
		resultMap.put("cartId", cartId);
		return resultMap;
	}

	@ApiOperation(value = "Cart Check", notes = "POST방식으로 카트 확인")
	@PostMapping(value = "/check/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Long> cartCheck(@Valid @RequestBody CartDTO cartDTO, BindingResult bidingResult) throws BindException {
		log.info(cartDTO);
		if(bidingResult.hasErrors()) {
			throw new BindException(bidingResult);
		}
		CartDTO resultDTO = cartService.cartCheck(cartDTO);
		Long cartId = resultDTO.getCartId();
		if(cartId > 0L) {
			cartService.addProdCnt(cartDTO);
		}
		Map<String, Long> resultMap = new HashMap<>();
		resultMap.put("cartId", cartId);
		return resultMap;
	}

	@ApiOperation(value = "Cart Modify Cnt", notes = "POST방식으로 카트 수량 변경")
	@PostMapping(value = "/modify/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Long> modifyCnt(@Valid @RequestBody CartDTO cartDTO, BindingResult bidingResult) throws BindException {
		log.info(cartDTO);
		if(bidingResult.hasErrors()) {
			throw new BindException(bidingResult);
		}
		cartService.modifyCnt(cartDTO);
		Map<String, Long> resultMap = new HashMap<>();
		resultMap.put("cartId", cartDTO.getCartId());
		return resultMap;		
	}
	
	
}
