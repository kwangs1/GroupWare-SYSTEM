package com.spring.web.Member.Controller;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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

	//회원 가입
	@GetMapping("/join")
	public void join() {
		log.info("회원가입 페이지 진입");
	}
	
	@PostMapping("/join")
	public String join(Model model, MemberVO member) {
		log.info("회원 가입 성공:" +member);
		service.join(member);
		
		return "redirect:/member/login";
	}
	
	//로그인
	@PostMapping("/login")
	public String login(MemberVO member, RedirectAttributes rttr
			,HttpServletRequest request) {
		HttpSession session = request.getSession();
		MemberVO mvo = service.login(member);
		log.info("login success");
		
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
}
