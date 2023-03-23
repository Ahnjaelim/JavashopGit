package kr.co.javashop.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProductListReviewCountDTO {
	
	private Long prodId;
	private String prodName;
	private int prodPrice;
	private String prodDesc;
	private LocalDateTime regDate;
	private Long reviewCount;
}
