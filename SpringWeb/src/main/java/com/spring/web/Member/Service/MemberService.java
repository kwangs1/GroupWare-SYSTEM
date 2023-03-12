package com.spring.web.Member.Service;

import com.spring.web.Member.VO.MemberVO;

public interface MemberService {

	//회원가입
	public void join(MemberVO member);
	//로그인
	public MemberVO login(MemberVO member);
}
