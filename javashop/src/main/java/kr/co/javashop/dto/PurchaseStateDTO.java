package kr.co.javashop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseStateDTO {
	
	private String purNo;
	private int purState;
	private int purTotalprice;
	private String memberId;
	
}
