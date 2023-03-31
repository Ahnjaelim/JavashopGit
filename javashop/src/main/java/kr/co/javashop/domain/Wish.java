package kr.co.javashop.domain;

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
public class Wish {

	@Id
	@Column(name = "wid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long wid;
	
	@Column(name = "prodid")
	private Long prodId;
	
	@Column(name = "mid")
	private String mid;
	
}
