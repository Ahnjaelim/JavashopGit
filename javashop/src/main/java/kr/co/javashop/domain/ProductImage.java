package kr.co.javashop.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
@ToString(exclude = "product")
public class ProductImage implements Comparable<ProductImage>{
	
	@Id
	private String uuid;
	
	private String fileName;
	private int ord;
	
	@ManyToOne
	private Product product;

	@Override
	public int compareTo(ProductImage other) {
		return this.ord-other.ord;
	}
	
	public void changeProduct(Product product) {
		this.product = product;
	}
	
	

}
