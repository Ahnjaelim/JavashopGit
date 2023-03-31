package kr.co.javashop.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import kr.co.javashop.domain.Wish;
import kr.co.javashop.dto.WishDTO;
import kr.co.javashop.dto.WishListDTO;
import kr.co.javashop.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class WishServiceImpl implements WishService {

	private final ModelMapper modelMapper;
	private final WishRepository wishRepository;
	
	@Override
	public Long register(WishDTO wishDTO) {
		Wish wish = modelMapper.map(wishDTO, Wish.class);
		Long wid = wishRepository.save(wish).getWid();
		return wid;
	}

	@Override
	public Long checkWish(String mid, Long prodId) {
		Optional<Wish> result = wishRepository.findByMidAndProdId(mid, prodId);
		Wish wish = result.orElseThrow();
		Long resultCode = 0L;
		if(result.isPresent()) {
			resultCode = wish.getWid();
		}
		return resultCode;
	}

	@Override
	public void remove(String mid, Long prodId) {
		Optional<Wish> result = wishRepository.findByMidAndProdId(mid, prodId);
		Wish wish = result.orElseThrow();
		Long wid = wish.getWid();
		wishRepository.deleteById(wid);
	}

	@Override
	public WishDTO readOne(String mid, Long prodId) {
		Optional<Wish> result = wishRepository.findByMidAndProdId(mid, prodId);
		WishDTO wishDTO = null;
		if(result.isPresent()) {
			wishDTO = modelMapper.map(result, WishDTO.class);
		}
		return wishDTO;
	}

	@Override
	public void remove(Long wid) {
		wishRepository.deleteById(wid);
	}

	@Override
	public List<WishListDTO> getAll(String mid) {
		// List<WishDTO> dtolist = wishRepository.findAllByMid(mid).stream().map(entity -> modelMapper.map(entity, WishDTO.class)).collect(Collectors.toList());
		List<WishListDTO> dtolist = wishRepository.findAllByMidJoin(mid);
		return dtolist;
	}

}
