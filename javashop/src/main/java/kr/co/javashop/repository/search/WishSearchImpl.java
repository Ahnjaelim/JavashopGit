package kr.co.javashop.repository.search;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;

import kr.co.javashop.domain.QProduct;
import kr.co.javashop.domain.QWish;
import kr.co.javashop.domain.Wish;
import kr.co.javashop.dto.WishListDTO;

public class WishSearchImpl extends QuerydslRepositorySupport implements WishSearch {

	public WishSearchImpl() {
		super(Wish.class);
	}

	@Override
	public List<WishListDTO> findAllByMidJoin(String mid) {
		
		QWish wish = QWish.wish;
		QProduct product = QProduct.product;
		JPQLQuery<Wish> query = from(wish);
		
		query.where(wish.mid.eq(mid));
		query.leftJoin(product).on(wish.prodId.eq(product.prodId));
		JPQLQuery<WishListDTO> dtoquery = query.select(Projections.bean(WishListDTO.class, 
			wish.wid,
			wish.mid,
			wish.prodId,
			product.prodName,
			product.prodPrice,
			product.prodFile
		));
		List<WishListDTO> dtolist = dtoquery.fetch();
		long count = dtoquery.fetchCount();
		return dtolist;
	}


}
