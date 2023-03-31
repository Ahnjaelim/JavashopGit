package kr.co.javashop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WishDTO {

	private Long wid;
	private Long prodId;
	private String mid;
		
}
