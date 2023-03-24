package kr.co.javashop.dto.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 파일 결과를 반환하는 객체

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadResultDTO {
	private String uuid;
	private String fileName;
	private boolean img;
	
	public String getLink() {
		if(img) {
			return "thumb_"+uuid+"_"+fileName;
		}else {
			return uuid+"_"+fileName;
		}
		
	}
}
