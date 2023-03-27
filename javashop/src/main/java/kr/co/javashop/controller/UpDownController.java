package kr.co.javashop.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.co.javashop.dto.upload.UploadFileDTO;
import kr.co.javashop.dto.upload.UploadResultDTO;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;

@RestController
@Log4j2
public class UpDownController {

	@Value("${upload.path}") // application.properties 값 불러오기
	private String uploadPath;
	
	@ApiOperation(value = "Upload POST", notes = "POST 방식으로 등록")
	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public List<UploadResultDTO> upload(UploadFileDTO uploadFileDTO) {
		log.info(uploadFileDTO);
		if(uploadFileDTO.getFiles()!=null) {
			final List<UploadResultDTO> list = new ArrayList<>();
			uploadFileDTO.getFiles().forEach(multipartFile->{
				String originalName = multipartFile.getOriginalFilename();
				log.info(originalName); // 파일 이름 출력
				String uuid = UUID.randomUUID().toString();
				Path savePath = Paths.get(uploadPath, uuid+"_"+originalName);
				boolean image = false;
				try {
					multipartFile.transferTo(savePath);
					log.info("업로드 성공! ("+uuid+"_"+originalName+")");
					if(Files.probeContentType(savePath).startsWith("image")) { // 파일이 이미지라면
						image = true;
						File thumbFile = new File(uploadPath, "thumb_"+uuid+"_"+originalName);
						// 이상하게도 한큐에 안되기때문에 프로젝트 리붓, 브라우저 재붓 후 스웨거 테스트
						Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 200, 200);
						log.info("썸네일 생성 성공! (thumb_"+uuid+"_"+originalName+")");
					}
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.info("업로드 실패");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.info("업로드 실패");
				}
				list.add(UploadResultDTO.builder().uuid(uuid).fileName(originalName).img(image).build());
				
			}); // end of each
			return list;
		}
		return null;
	} // end of method
	
	/*
	 * <ResponseEntitiy>
	 * ResponseEntity는 개발자가 직접 결과 데이터와 HTTP 상태 코드를 직접 제어할 수 있는 클래스로 
	 * 404나 500 ERROR와 같은 HTTP 상태코드를 업로드 데이터와 함께 전송할 수 있다. 
	 * 때문에 좀 더 세밀한 제어가 필요한 경우 사용한다.
	 */
	@ApiOperation(value = "view 파일", notes = "GET방식으로 첨부파일 조회")
	@GetMapping("/view/javashop/{fileName}")
	public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName){
		Resource resource = new FileSystemResource(uploadPath+File.separator+fileName);
		String resourceName = resource.getFilename();
		HttpHeaders headers = new HttpHeaders();
		try {
			headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok().headers(headers).body(resource);
	}
	
	@ApiOperation(value = "remove 파일", notes = "DELETE 방식으로 파일 삭제")
	@DeleteMapping("/remove/{fileName}")
	public Map<String, Boolean> removeFile(@PathVariable String fileName){
		Resource resource = new FileSystemResource(uploadPath+File.separator+fileName);
		String resourceName = resource.getFilename();
		Map<String, Boolean> resultMap = new HashMap<>();
		boolean removed = false;
		try {
			String contentType = Files.probeContentType(resource.getFile().toPath());
			removed = resource.getFile().delete();
			// (이미지 파일이라) 썸네일이 존재 한다면
			if(contentType.startsWith("image")) {
				File thumbnailFile = new File(uploadPath+File.separator+"thumb_"+fileName);
				thumbnailFile.delete();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		resultMap.put("result", removed);
		return resultMap;
	}
}
