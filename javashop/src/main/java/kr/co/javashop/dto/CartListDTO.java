package kr.co.javashop.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

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
public class CartListDTO {
	private Long cartId;
	private String memberId;
	private Long prodId;
	private int prodCnt;

    private String prodName;
    private String cateCode;
    private int prodPrice;
    private int prodStock;
    private String prodDesc;
    private int prodHit;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private List<String> fileNames;    
}
