package kr.co.javashop.repository;

import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;

import kr.co.javashop.domain.Member;
import kr.co.javashop.domain.MemberRole;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class MemberRepositoryTest {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// @Test
	public void insertMember() {
		IntStream.rangeClosed(1, 100).forEach(i -> {
			
			Member member = Member.builder()
					.mid("user"+i)
					.mpw(passwordEncoder.encode("1111"))
					.email("email"+i+"@aaa.bbb")
					.build();
			member.addRole(MemberRole.USER);
			if(i >= 90) {
				member.addRole(MemberRole.ADMIN);
			}
			memberRepository.save(member);
			
			
		});
	}
	
	// @Test
	public void testRead() {
		Optional<Member> result = memberRepository.getWithRoles("user100");
		Member member = result.orElseThrow();
		
		log.info(member);
		log.info(member.getRoleSet());
		
		member.getRoleSet().forEach(memberRole -> log.info(memberRole.name()));
	}
	
	@Commit
	@Test
	public void testUpdate() {
		String mid = "thisisddd@daum.net";
		String mpw = passwordEncoder.encode("54321");
		memberRepository.updatePassword(mpw, mid);
	}
}
