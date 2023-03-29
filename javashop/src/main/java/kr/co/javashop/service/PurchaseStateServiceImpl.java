package kr.co.javashop.service;

import java.util.List;
import java.util.stream.Collectors;

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

	@Override
	public List<PurchaseStateDTO> getAll() {
		List<PurchaseState> result = purchaseStateRepository.findAll(); 
		List<PurchaseStateDTO> dtolist = purchaseStateRepository.findAll().stream().map(
				entity -> modelMapper.map(entity, PurchaseStateDTO.class)).collect(Collectors.toList());
		return dtolist;
	}

	@Override
	public List<PurchaseStateDTO> getAllByMemberId(String memberId) {
		List<PurchaseState> result = purchaseStateRepository.findAll(); 
		List<PurchaseStateDTO> dtolist = purchaseStateRepository.findAllByMemberId(memberId).stream().map(
				entity -> modelMapper.map(entity, PurchaseStateDTO.class)).collect(Collectors.toList());
		return dtolist;
	}

	
}
