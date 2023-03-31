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
public class WishListDTO {

	private Long wid;
	private String mid;
	private Long prodId;
	private String prodName;
	private int prodPrice;
	private String prodFile;
	
}
