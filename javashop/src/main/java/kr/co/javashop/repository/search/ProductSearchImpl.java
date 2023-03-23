package kr.co.javashop.repository.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;

import kr.co.javashop.domain.Product;
import kr.co.javashop.domain.QProduct;
import kr.co.javashop.domain.QReview;
import kr.co.javashop.dto.ProductListReviewCountDTO;

public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {
    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     * @param domainClass must not be {@literal null}.
     */
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
        JPQLQuery<Product> query = from(product); // select from recipe 객체
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
        		product.regDate,
        		review.count().as("reviewCount")
        		));

        this.getQuerydsl().applyPagination(pageable, dtoQuery);
        List<ProductListReviewCountDTO> dtoList = dtoQuery.fetch();
        long count = dtoQuery.fetchCount();
        
		return new PageImpl<>(dtoList, pageable, count);
	}

}
