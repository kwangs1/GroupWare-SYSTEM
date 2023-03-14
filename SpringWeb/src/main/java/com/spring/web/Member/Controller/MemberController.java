package com.spring.web.Member.Controller;

import java.util.Random;
import java.util.logging.Logger;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.web.Member.Service.MemberService;
import com.spring.web.Member.VO.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {
	private static Logger log = Logger.getLogger(MemberController.class.getName());
	
	@Autowired
	private MemberService service;
	@Autowired
	private JavaMailSender mailSender;

	//회원 가입
	@GetMapping("/join")
	public void join() {
		//log.info("회원가입 페이지 진입");
	}
	
	@PostMapping("/join")
	public String join(Model model, MemberVO member) {
		//log.info("회원 가입 성공:" +member);
		service.join(member);
		
		return "redirect:/member/login";
	}
	
	//로그인
	@PostMapping("/login")
	public String login(MemberVO member, RedirectAttributes rttr
			,HttpServletRequest request) {
		HttpSession session = request.getSession();
		MemberVO mvo = service.login(member);
		//log.info("login success");
		
		if(mvo == null) {
			int result = 0;
			rttr.addFlashAttribute("result",result);
			return "redirect:/member/login";
		}
		
		session.setAttribute("member", mvo);
		return "redirect:/";
		
	}
	
	@GetMapping("/login")
	public void login() {
		
	}
	
	//로그아웃
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		session.invalidate();
		
		return "redirect:/";
	}
	
	//세션 유지시간
	@GetMapping("/session-check")
	@ResponseBody
	public String sessionCheck(HttpSession session) {
		if(session.getAttribute("member") != null) {
			return "Session is still alive.";	
		}else {
			return "Session is still alive.";
		}
	}
	
	//id 중복검사
	@PostMapping("/idcheck")
	@ResponseBody
	public String idcheck(String id) {
		//log.info("idcheck Controller");	    
		int result = service.idcheck(id);
		
		if(result == 0) {
			//log.info("없는아이디에요");
			return "success";
		}else {
			//log.info("아이디 주인 있어요");
			return "fail";
		}
	}
	
	//이름 같은 동명이인 구별을 위한 이름 체크
	@PostMapping("/namecheck")
	@ResponseBody
	public String namecheck(String name) {
		int result = service.namecheck(name);
		
		if(result == 0) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	//이메일 인증
	@PostMapping("/mailcheck")
	@ResponseBody
	public String mailcheck(String email) {
		//랜덤 번호 발행할 변수 선언
		Random random = new Random();
		int checkNum = random.nextInt(88888)+11111;
		
		//발신 이메일
		String setFrom = "kwang095@naver.com";
		//수신 이메일
		String toMail = email;
		//메일 제목
		String title = "회원가입 인증번호 발송 메일입니다.";
		//메일 내용
		String content = "사용자가 본인임을 확인하는 확인 코드를 입력하세요."
				+"<br><br>"
				+"인증번호는" + checkNum + "입니다";
		
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			helper.setText(content,true);
			mailSender.send(message);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		String num = Integer.toString(checkNum);
		return num;
	}
}
