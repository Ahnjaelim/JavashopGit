package kr.co.javashop.repository.search;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;

import kr.co.javashop.domain.Cart;
import kr.co.javashop.domain.QCart;
import kr.co.javashop.domain.QProduct;
import kr.co.javashop.dto.CartListDTO;

public class CartSearchImpl extends QuerydslRepositorySupport implements CartSearch {

	public CartSearchImpl() {
		super(Cart.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<CartListDTO> findByMemberId(String memberId) {
		
		QCart cart = QCart.cart;
		QProduct product = QProduct.product;
        JPQLQuery<Cart> query = from(cart);
        
		query.where(cart.memberId.eq(memberId));
		query.leftJoin(product).on(cart.prodId.eq(product.prodId));
		JPQLQuery<CartListDTO> dtoquery = query.select(Projections.bean(CartListDTO.class,
				cart.cartId,
				cart.memberId,
				cart.prodId,
				cart.prodCnt,
				product.prodName,
				product.cateCode,
				product.prodPrice,
				product.prodStock,
				cart.regDate));
		List<CartListDTO> dtolist = dtoquery.fetch();
		long count = dtoquery.fetchCount();
		return dtolist;      
	}

}
