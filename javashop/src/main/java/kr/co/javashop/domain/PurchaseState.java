package kr.co.javashop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PurchaseState extends BaseEntity {

	@Id
	@Column(name = "purno")
	private String purNo;
	
    @Column(name = "purstate")
	private int purState;
    
    @Column(name = "purtotalprice")
	private int purTotalprice;

    @Column(name = "memberid")
	private String memberId;
}
