package kr.co.javashop.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;

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
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "catecode")
	private String cateCode;
    
    @Column(name = "catename")   
	private String cateName;
    
    
    @Column(name = "cateparent") 
	private Long cateParent;
	
	@Column(name = "catedepth")
    @ColumnDefault("0")    	
	private int cateDepth;
	
}
