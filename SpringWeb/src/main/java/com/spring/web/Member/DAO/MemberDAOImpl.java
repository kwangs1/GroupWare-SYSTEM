package com.spring.web.Member.DAO;

import java.util.logging.Logger;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.web.Member.VO.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO{
	private static Logger log = Logger.getLogger(MemberDAO.class.getName());
	
	@Autowired
	private SqlSession session;
	
	//ȸ������
	@Override
	public void join(MemberVO member) {
		//log.info("join dao");
		session.insert("mapper.Member.join",member);
	}
	
	//�α���
	@Override
	public MemberVO login(MemberVO member) {
		//log.info("login dao");
		return session.selectOne("mapper.Member.login",member);
	}
	
	//id �ߺ��˻�
	@Override
	public int idcheck(String id) {
		//log.info("idcheck dao");
		return session.selectOne("mapper.Member.idcheck",id);
	}
	
	//�̸� üũ
	@Override
	public int namecheck(String name) {
		return session.selectOne("mapper.Member.namecheck",name);
	}
}
