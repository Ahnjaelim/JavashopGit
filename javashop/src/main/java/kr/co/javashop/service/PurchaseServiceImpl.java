package kr.co.javashop.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import kr.co.javashop.domain.Purchase;
import kr.co.javashop.dto.PurchaseDTO;
import kr.co.javashop.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class PurchaseServiceImpl implements PurchaseService{

	private final ModelMapper modelMapper;
	private final PurchaseRepository purchaseRepository;
	
	@Override
	public Long register(PurchaseDTO purchaseDTO) {
		Purchase purchase = modelMapper.map(purchaseDTO, Purchase.class);
		Long purId = purchaseRepository.save(purchase).getPurId();
		return purId;
	}

	@Override
	public List<PurchaseDTO> getAll() {
		List<PurchaseDTO> dtolist = purchaseRepository.findAll().stream().map(
				entity -> modelMapper.map(entity, PurchaseDTO.class)).collect(Collectors.toList()); 
		return dtolist;
	}

	@Override
	public List<PurchaseDTO> getAllByPurNo(String purNo) {
		List<PurchaseDTO> dtolist = purchaseRepository.findAllByPurNo(purNo).stream().map(
				entity -> modelMapper.map(entity, PurchaseDTO.class)).collect(Collectors.toList()); 
		return dtolist;
	}

}
