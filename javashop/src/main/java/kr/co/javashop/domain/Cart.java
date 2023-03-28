package kr.co.javashop.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class Cart extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cartid")
	private Long cartId;
	
	@Column(name = "memberid")
	private String memberId;
	
	@Column(name = "prodid")
	private Long prodId;
	
	@Column(name = "prodcnt")
	private int prodCnt;
	
	public void addProdCnt(int prodCnt) {
		this.prodCnt = this.prodCnt + prodCnt; 
	}
	
	public void changCnt(int prodCnt) {
		this.prodCnt = prodCnt;
	}
}
