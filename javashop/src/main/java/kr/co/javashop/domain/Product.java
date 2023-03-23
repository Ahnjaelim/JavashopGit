package kr.co.javashop.domain;

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
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prodid")
    private Long prodId;
    
    @Column(name = "prodname", length = 200, nullable = false)
    private String prodName;
    
    @Column(name = "catecode", length = 50, nullable = false)
    private String cateCode;
    
    @Column(name = "prodprice", length = 10, nullable = false)
    private int prodPrice;
    
    @Column(name = "prodstock", length = 10, nullable = false)
    private int prodStock;
    
    @Column(name = "proddesc", length = 2000, nullable = false)
    private String prodDesc;
    
    @Column(name = "prodhit", length = 10, nullable = false)
    @ColumnDefault("0")
    private int prodHit;

    public void change(String prodName, String cateCode, int prodPrice, int prodStock, String prodDesc) {
        this.prodName = prodName;
        this.cateCode = cateCode;
        this.prodPrice = prodPrice;
        this.prodStock = prodStock;
        this.prodDesc = prodDesc;
    }
}
