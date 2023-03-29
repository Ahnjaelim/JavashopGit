package kr.co.javashop.service;

import kr.co.javashop.dto.MemberJoinDTO;

public interface MemberService {

	static class MidExistException extends Exception {
		
	}
	
	void join(MemberJoinDTO memberJoinDTO) throws MidExistException;
}
