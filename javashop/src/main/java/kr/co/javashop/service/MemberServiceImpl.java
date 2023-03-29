package kr.co.javashop.service;



import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.javashop.domain.Member;
import kr.co.javashop.domain.MemberRole;
import kr.co.javashop.dto.MemberJoinDTO;
import kr.co.javashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final ModelMapper modelMapper;
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public void join(MemberJoinDTO memberJoinDTO) throws MidExistException {
		String mid = memberJoinDTO.getMid();
		boolean exist = memberRepository.existsById(mid);
		// boolean exist = false;
		if(exist) {
			throw new MidExistException();
		}
		Member member = modelMapper.map(memberJoinDTO, Member.class);
		member.changePassword(passwordEncoder.encode(memberJoinDTO.getMpw()));
		member.addRole(MemberRole.USER);
		
		log.info("==============================");
		log.info(member);
		log.info(member.getRoleSet());
		
		memberRepository.save(member);
		
	}

}
