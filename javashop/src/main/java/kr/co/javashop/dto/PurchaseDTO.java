package kr.co.javashop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDTO {

	private Long purId;
    private String purNo;
	private Long prodId;
	private int prodCnt;
	private int purTotalprice;
	private int purState;
	private int purDelivery;
	private String memberId;
	
}
