package com.spring.web.Member.Service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.web.Member.DAO.MemberDAO;
import com.spring.web.Member.VO.MemberVO;

@Service
public class MemberServiceImpl implements MemberService{
	private static Logger log = Logger.getLogger(MemberService.class.getName()); 
	
	@Autowired
	private MemberDAO dao;

	//ȸ������
	@Override
	public void join(MemberVO member) {
		log.info("join service");
		dao.join(member);
	}
	
	//�α���
	@Override
	public MemberVO login(MemberVO member) {
		log.info("login info");
		return dao.login(member);
	}
}
