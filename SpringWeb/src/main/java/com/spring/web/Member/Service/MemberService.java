package com.spring.web.Member.Service;

import com.spring.web.Member.VO.MemberVO;

public interface MemberService {

	//ȸ������
	public void join(MemberVO member);
	//�α���
	public MemberVO login(MemberVO member);
}
