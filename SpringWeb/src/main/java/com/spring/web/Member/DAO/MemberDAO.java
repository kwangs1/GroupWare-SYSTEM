package com.spring.web.Member.DAO;

import com.spring.web.Member.VO.MemberVO;

public interface MemberDAO {

	//ȸ������
	public void join(MemberVO member);
	//�α���
	public MemberVO login(MemberVO member);
}