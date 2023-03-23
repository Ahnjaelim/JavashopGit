package kr.co.javashop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Review", indexes = {@Index(name = "idx_review_product_prodid", columnList = "product_prodid")})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "product")
public class Review extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "revid")
	private Long revId;

	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;
	
	@Column(name = "revtext")
	private String revText;
	
	@Column(name = "revname")
	private String revName;
	
	public void changeText(String text) {
		this.revText = text;
	}
}
