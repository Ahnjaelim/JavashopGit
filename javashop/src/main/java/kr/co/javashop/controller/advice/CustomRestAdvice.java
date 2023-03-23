package kr.co.javashop.controller.advice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.log4j.Log4j2;

/* REST 방식의 컨트롤러는 대부분 Ajax와 같이 눈에 보이지 않는 방식으로 서버를 호출하고 전송하기 때문에
 * 에러가 발생하면 어디에서 어떤 에러가 발생했는지 찾기가 힘들다.
 * 때문에 @Valid 과정에서 문제가 발생하면 처리할 수 있도록 @RestControllerAdvice를 설계한다.
 */

@RestControllerAdvice
@Log4j2
public class CustomRestAdvice {
	
	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	public ResponseEntity<Map<String, String>> handleBindException(BindException e){
		log.error(e);
		Map<String, String> errorMap = new HashMap<>();
		if(e.hasErrors()) {
			BindingResult bindingResult = e.getBindingResult();
			bindingResult.getFieldErrors().forEach(fieldError ->{
				errorMap.put(fieldError.getField(), fieldError.getCode());
			});
		}
		return ResponseEntity.badRequest().body(errorMap);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	public ResponseEntity<Map<String, String>> handleFKException(Exception e){
		log.error(e);
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put("time", ""+System.currentTimeMillis());
		errorMap.put("msg", "constraint fails");
		return ResponseEntity.badRequest().body(errorMap);
		
	}
	@ExceptionHandler({NoSuchElementException.class, EmptyResultDataAccessException.class})
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	public ResponseEntity<Map<String, String>> handleNoSuchElement(Exception e){
		log.error(e);
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put("time", ""+System.currentTimeMillis());
		errorMap.put("msg", "No Such Element Exception");
		return ResponseEntity.badRequest().body(errorMap);
	}
}
