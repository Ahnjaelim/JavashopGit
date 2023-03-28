package kr.co.javashop.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import kr.co.javashop.domain.PurchaseState;
import kr.co.javashop.dto.PurchaseStateDTO;
import kr.co.javashop.repository.PurchaseStateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class PurchaseStateServiceImpl implements PurchaseStateService{

	private final ModelMapper modelMapper;
	private final PurchaseStateRepository purchaseStateRepository;
	
	@Override
	public String register(PurchaseStateDTO purchaseStateDTO) {
		PurchaseState purchaseState = modelMapper.map(purchaseStateDTO, PurchaseState.class);
		String purNo = purchaseStateRepository.save(purchaseState).getPurNo();
		return purNo;
	}

	
}
