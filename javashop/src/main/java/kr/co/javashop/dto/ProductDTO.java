package kr.co.javashop.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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
    private LocalDateTime regDate;
    private Timestamp modDate;
}
