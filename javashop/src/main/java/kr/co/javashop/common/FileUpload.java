package kr.co.javashop.common;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class FileUpload {

	@Value("${upload.path}")
	private String uploadPath;	
	
	public String fileUpload(MultipartFile file) {
		// Long unixtime =  System.currentTimeMillis();
		String uuid = UUID.randomUUID().toString();
		String fileName = uuid + "_" + file.getOriginalFilename();
		// 이클립스 새로 고침 문제
		// String rootPath = System.getProperty("user.dir");
		// String path = rootPath + "/src/main/resources/static/files/";
		try{
			// String path = WebUtils.getRealPath(request.getSession().getServletContext(), "/resources/data/recipe/");
            File folder = new File(uploadPath);
            if (!folder.exists()) folder.mkdirs();
            File destination = new File(uploadPath + File.separator + fileName);
            file.transferTo(destination);
            System.out.println(destination);
            System.out.println("성공!");
        }catch (Exception e){
        	System.out.println("실패!");
        }
		return fileName;
	}	
	
}
