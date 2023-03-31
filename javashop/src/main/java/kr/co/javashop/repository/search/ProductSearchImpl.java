package kr.co.javashop.repository.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

import kr.co.javashop.domain.Product;
import kr.co.javashop.domain.QProduct;
import kr.co.javashop.domain.QReview;
import kr.co.javashop.domain.QWish;
import kr.co.javashop.domain.Wish;
import kr.co.javashop.dto.ProductImageDTO;
import kr.co.javashop.dto.ProductListAllDTO;
import kr.co.javashop.dto.ProductListReviewCountDTO;

public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {

    public ProductSearchImpl() {
        super(Product.class);
    }

    @Override
    public Page<Product> search(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Product> searchAll(String[] types, String keyword, String category, String[] states, Pageable pageable) {

        QProduct product = QProduct.product; // Q도메인 객체
        QWish wish = QWish.wish;
        JPQLQuery<Product> query = from(product); // select from recipe 객체

        if((types != null && types.length > 0) && keyword != null) { // 검색 조건과 키워드가 있다면
            // where절
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for(String type : types) {
                System.out.println(type);
                switch(type) {
                    case "prodName" :
                        booleanBuilder.or(product.prodName.contains(keyword));
                        break;
                    case "prodDesc" :
                        booleanBuilder.or(product.prodDesc.contains(keyword));
                        break;
                } // end of switch
            } // end of for
            query.where(booleanBuilder);
        }// end of if

        // 카테고리 검색
        if(category != null) {
            query.where(product.cateCode.contains(category));
        }

        // rno > 0
        query.where(product.prodId.gt(0L));

        // 페이징
        this.getQuerydsl().applyPagination(pageable, query);

        // 쿼리 실행
        List<Product> list = query.fetch();
        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }

	@Override
	public Page<ProductListReviewCountDTO> searchWithReviewCount(String[] types, String keyword, String category,
			String[] states, Pageable pageable) {
		
        QProduct product = QProduct.product; // Q도메인 객체
        QReview review = QReview.review;
        JPQLQuery<Product> query = from(product); // select from Product엔티티
        query.leftJoin(review).on(review.product.eq(product)); // product 테이블을 review 테이블과 레프트 조인
        query.groupBy(product);
        
        if((types != null && types.length > 0) && keyword != null) { // 검색 조건과 키워드가 있다면
            // where절
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for(String type : types) {
                System.out.println(type);
                switch(type) {
                    case "prodName" :
                        booleanBuilder.or(product.prodName.contains(keyword));
                        break;
                    case "prodDesc" :
                        booleanBuilder.or(product.prodDesc.contains(keyword));
                        break;
                } // end of switch
            } // end of for
            query.where(booleanBuilder);
        }// end of if

        // 카테고리 검색
        if(category != null) {
            query.where(product.cateCode.contains(category));
        }

        // rno > 0
        query.where(product.prodId.gt(0L));
        
        JPQLQuery<ProductListReviewCountDTO> dtoQuery = query.select(Projections.bean(ProductListReviewCountDTO.class, 
        		product.prodId,
        		product.prodName,
        		product.prodDesc,
        		product.prodPrice,
        		product.prodStock,
        		product.prodFile,
        		product.regDate,
        		review.count().as("reviewCount")
        ));

        this.getQuerydsl().applyPagination(pageable, dtoQuery);
        List<ProductListReviewCountDTO> dtoList = dtoQuery.fetch();
        long count = dtoQuery.fetchCount();
        
		return new PageImpl<>(dtoList, pageable, count);
	}

	@Override
	public Page<ProductListAllDTO> searchWithAll1(String[] types, String keyword, String category, String[] states, Pageable pageable) {
		
        QProduct product = QProduct.product;
        QReview review = QReview.review;
        
        JPQLQuery<Product> productJPQLquery = from(product); // select from Product엔티티
        productJPQLquery.leftJoin(review).on(review.product.eq(product)); // product 테이블을 review 테이블과 레프트 조인
        getQuerydsl().applyPagination(pageable, productJPQLquery); // 페이징
        
        List<Product> productList = productJPQLquery.fetch();
        productList.forEach(product1 -> {
        	System.out.println(product1.getProdId());
        	System.out.println(product1.getImageSet());
        	System.out.println("------------------------------");
        });

        return null;
		// return new PageImpl<>(dtoList, pageable, totalCount);
	}

	@Override
	public Page<ProductListAllDTO> searchWithAll(String[] types, String keyword, String category, String[] states, Pageable pageable) {

        QProduct product = QProduct.product;
        QReview review = QReview.review;
        QWish wish = QWish.wish;
        
        JPQLQuery<Product> productJPQLquery = from(product); // select from Product엔티티
        productJPQLquery.leftJoin(review).on(review.product.eq(product)); // product 테이블을 review 테이블과 레프트 조인

        // ================================================== 검색처리
        
        if((types != null && types.length > 0) && keyword != null) { // 검색 조건과 키워드가 있다면
            // where절
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for(String type : types) {
                System.out.println(type);
                switch(type) {
                    case "prodName" :
                        booleanBuilder.or(product.prodName.contains(keyword));
                        break;
                    case "prodDesc" :
                        booleanBuilder.or(product.prodDesc.contains(keyword));
                        break;
                } // end of switch
            } // end of for
            productJPQLquery.where(booleanBuilder);
        }// end of if

        // 카테고리 검색
        if(category != null) {
        	productJPQLquery.where(product.cateCode.contains(category));
        }

        // rno > 0
        productJPQLquery.where(product.prodId.gt(0L));        
        
        // ================================================== group by
        
        productJPQLquery.groupBy(product);
        getQuerydsl().applyPagination(pageable, productJPQLquery); // 페이징
        	
        JPQLQuery<Tuple> tupleJPQLQuery = productJPQLquery.select(product, review.countDistinct(), 
        		JPAExpressions.select(wish.countDistinct()).from(wish).where(wish.prodId.eq(product.prodId))
        );
        
        List<Tuple> tupleList = tupleJPQLQuery.fetch();
        List<ProductListAllDTO> dtoList = tupleList.stream().map(tuple -> {
        	
        	Product product1 = (Product) tuple.get(product);
        	long reviewCount = tuple.get(1, Long.class);
        	long wishCount = tuple.get(2, Long.class);
        	ProductListAllDTO dto = ProductListAllDTO.builder()
    			.cateCode(product1.getCateCode())
    			.prodId(product1.getProdId())
    			.prodName(product1.getProdName())
    			.prodDesc(product1.getProdDesc())
    			.prodPrice(product1.getProdPrice())
    			.prodStock(product1.getProdStock())
    			.prodFile(product1.getProdFile())
    			.prodWish(wishCount)
    			.regDate(product1.getRegDate())
    			.reviewCount(reviewCount)
    			.build();
        	
        	// ProductImage를 ProductImageDTO로 변환
           	List<ProductImageDTO> imageDTOS = product1.getImageSet().stream().sorted().map(productImage -> ProductImageDTO.builder()
				.uuid(productImage.getUuid())
				.fileName(productImage.getFileName())
				.ord(productImage.getOrd())
				.build()
        	).collect(Collectors.toList());
           	
           	dto.setProductImages(imageDTOS);        	
        	return dto;
        }).collect(Collectors.toList());
        long totalCount = productJPQLquery.fetchCount(); 
        
		return new PageImpl<>(dtoList, pageable, totalCount);
				
	}

}
