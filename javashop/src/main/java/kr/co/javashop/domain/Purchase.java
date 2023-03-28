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
public class Purchase extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purid")
	private Long purId;
    
    @Column(name = "purno")
    private String purNo;
    
    @Column(name = "prodid")
	private Long prodId;
    
    @Column(name = "prodcnt")
	private int prodCnt;
    
    @Column(name = "purtotalprice")
	private int purTotalprice;
    
    @Column(name = "purstate")
	private int purState;
    
    @Column(name = "purdelivery")
	private int purDelivery;
    
    @Column(name = "memberid")
	private String memberId;
	
}
