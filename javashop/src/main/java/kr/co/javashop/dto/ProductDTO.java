package kr.co.javashop.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import kr.co.javashop.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
	
    private Long prodId;
    
    @NotEmpty
    @Size(min = 3, max = 200)
    private String prodName;
    
    @NotEmpty
    private String cateCode;
    
    private int prodPrice;
    
    private int prodStock;
    
    @NotEmpty
    private String prodDesc;
    
    private int prodHit;

    private MultipartFile multipartFile;
    private String prodFile;
    
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    

    
    // 첨부파일 이름들
    private List<String> fileNames;
    
}
