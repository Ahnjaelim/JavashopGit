package kr.co.javashop.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductListAllDTO {
	
    private Long prodId;
    private String prodName;
    private String cateCode;
    private int prodPrice;
    private int prodStock;
    private String prodDesc;
    private int prodHit;
    private String prodFile;
    private Long prodWish;
    private LocalDateTime regDate;
    private Timestamp modDate;
	private Long reviewCount;
	private List<ProductImageDTO> productImages;
}
