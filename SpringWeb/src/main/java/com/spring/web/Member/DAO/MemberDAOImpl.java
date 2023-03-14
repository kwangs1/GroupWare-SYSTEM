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
	
	//회원가입
	@Override
	public void join(MemberVO member) {
		//log.info("join dao");
		session.insert("mapper.Member.join",member);
	}
	
	//로그인
	@Override
	public MemberVO login(MemberVO member) {
		//log.info("login dao");
		return session.selectOne("mapper.Member.login",member);
	}
	
	//id 중복검사
	@Override
	public int idcheck(String id) {
		//log.info("idcheck dao");
		return session.selectOne("mapper.Member.idcheck",id);
	}
	
	//이름 체크
	@Override
	public int namecheck(String name) {
		return session.selectOne("mapper.Member.namecheck",name);
	}
}
