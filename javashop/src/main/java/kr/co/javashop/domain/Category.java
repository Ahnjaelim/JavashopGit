package kr.co.javashop.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "cateChildren")
public class Category {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "catecode")
    private Long cateCode;
    
    @Column(name = "catename")
    private String cateName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cateparent")
    private Category cateParent;

    @Column(name = "catedepth")
    private Long cateDepth;

    @OneToMany(mappedBy = "cateParent")
    private List<Category> cateChildren = new ArrayList<>();
}