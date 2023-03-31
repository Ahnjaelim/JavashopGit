package kr.co.javashop.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.BatchSize;
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
@ToString(exclude = "imageSet")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prodid")
    private Long prodId;
    
    @Column(name = "prodname", length = 255, nullable = false)
    private String prodName;
    
    @Column(name = "catecode", length = 50, nullable = false)
    private String cateCode;
	/*
    @ManyToOne(targetEntity=Category.class, fetch=FetchType.LAZY)
	@JoinColumn(name="catecode")
	private Category category;    
    */
    
    @Column(name = "prodprice", length = 10, nullable = false)
    private int prodPrice;
    
    @Column(name = "prodstock", length = 10, nullable = false)
    private int prodStock;
    
    @Column(name = "proddesc", length = 2000, nullable = false)
    private String prodDesc;
    
    @Column(name = "prodhit", length = 10, nullable = false)
    @ColumnDefault("0")
    private int prodHit;
    
    @Column(name = "prodfile", length = 255, nullable = true)
    private String prodFile;

    public void change(String prodName, String cateCode, int prodPrice, int prodStock, String prodDesc) {
        this.prodName = prodName;
        this.cateCode = cateCode;
        this.prodPrice = prodPrice;
        this.prodStock = prodStock;
        this.prodDesc = prodDesc;
    }
    
    public void changeThumbnail(String prodFile) {
    	this.prodFile = prodFile;
    }
    
    // 하위 테이블인 product_image를 관리
    // LAZY : 지연 로딩, EAGER : 즉시 로딩
    // orphanRemoval = true : 하위 엔티티 삭제
    @OneToMany(mappedBy = "product", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true) // ProductImage에 있는 private Product product; 필드
    @Builder.Default
    @BatchSize(size = 20)
    private Set<ProductImage> imageSet = new HashSet<>();
    
    // 이미지 업로드시 imageSet에 ProductImage타입의 데이터가 리스트로 저장되고 Product에 게시글을 insert하면 ProductImage테이블에도 같이 insert됨
    
    public void addImage(String uuid, String fileName) {
    	ProductImage productImage = ProductImage.builder()
    			.uuid(uuid)
    			.fileName(fileName)
    			.product(this)
    			.ord(imageSet.size())
    			.build();
    	imageSet.add(productImage);
    }
    
    public void clearImages() {
    	imageSet.forEach(productImage -> productImage.changeProduct(null));
    	this.imageSet.clear();
    }
    
}
