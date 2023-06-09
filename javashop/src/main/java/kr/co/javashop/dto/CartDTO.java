package kr.co.javashop.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
	private Long cartId;
	private String memberId;
	private Long prodId;
	private int prodCnt;
    private LocalDateTime regDate;
    private LocalDateTime modDate;	
}
