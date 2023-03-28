package kr.co.javashop.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import kr.co.javashop.domain.Cart;
import kr.co.javashop.dto.CartDTO;
import kr.co.javashop.dto.CartListDTO;
import kr.co.javashop.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

	private final ModelMapper modelMapper;
	private final CartRepository cartRepository;
	
	@Override
	public Long register(CartDTO cartDTO) {
		Cart cart = modelMapper.map(cartDTO, Cart.class); 
		Long cartId = cartRepository.save(cart).getCartId();  
		return cartId;
	}

	@Override
	public CartDTO cartCheck(CartDTO cartDTO) {
		Optional<Cart> result = cartRepository.findByMemberIdAndProdId(cartDTO.getMemberId(), cartDTO.getProdId());
		Cart cart = result.orElseThrow();
		CartDTO resultDTO = modelMapper.map(cart, CartDTO.class);
		return resultDTO;
	}

	@Override
	public void addProdCnt(CartDTO cartDTO) {
		Optional<Cart> result = cartRepository.findByMemberIdAndProdId(cartDTO.getMemberId(), cartDTO.getProdId());
		Cart cart = result.orElseThrow();
		cart.addProdCnt(cartDTO.getProdCnt());
		cartRepository.save(cart);
	}
	
	@Override
	public void modifyCnt(CartDTO cartDTO) {
		Optional<Cart> result = cartRepository.findById(cartDTO.getCartId());
		Cart cart = result.orElseThrow();
		cart.changCnt(cartDTO.getProdCnt());
		cartRepository.save(cart);
	}
	
	@Override
	public List<CartListDTO> getByMemberId(String memberId) {
		/*
		 List<CartDTO> cartDTO = cartRepository.findAllByMemberIdOrderByCartIdDesc(memberId).stream()
				.map(entity -> modelMapper.map(entity, CartDTO.class))
				.collect(Collectors.toList());
		*/
		List<CartListDTO> dtolist = cartRepository.findByMemberId(memberId);
		return dtolist;
	}

	@Override
	public CartDTO readOne(Long cartId) {
		Optional<Cart> result = cartRepository.findById(cartId);
		Cart cart = result.orElseThrow();
		CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
		return cartDTO;
	}

	@Override
	public void remove(Long cartId) {
		cartRepository.deleteById(cartId);
	}




}
