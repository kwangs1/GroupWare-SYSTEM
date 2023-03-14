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

	//ȸ�� ����
	@GetMapping("/join")
	public void join() {
		//log.info("ȸ������ ������ ����");
	}
	
	@PostMapping("/join")
	public String join(Model model, MemberVO member) {
		//log.info("ȸ�� ���� ����:" +member);
		service.join(member);
		
		return "redirect:/member/login";
	}
	
	//�α���
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
	
	//�α׾ƿ�
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		session.invalidate();
		
		return "redirect:/";
	}
	
	//���� �����ð�
	@GetMapping("/session-check")
	@ResponseBody
	public String sessionCheck(HttpSession session) {
		if(session.getAttribute("member") != null) {
			return "Session is still alive.";	
		}else {
			return "Session is still alive.";
		}
	}
	
	//id �ߺ��˻�
	@PostMapping("/idcheck")
	@ResponseBody
	public String idcheck(String id) {
		//log.info("idcheck Controller");	    
		int result = service.idcheck(id);
		
		if(result == 0) {
			//log.info("���¾��̵𿡿�");
			return "success";
		}else {
			//log.info("���̵� ���� �־��");
			return "fail";
		}
	}
	
	//�̸� ���� �������� ������ ���� �̸� üũ
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
	
	//�̸��� ����
	@PostMapping("/mailcheck")
	@ResponseBody
	public String mailcheck(String email) {
		//���� ��ȣ ������ ���� ����
		Random random = new Random();
		int checkNum = random.nextInt(88888)+11111;
		
		//�߽� �̸���
		String setFrom = "kwang095@naver.com";
		//���� �̸���
		String toMail = email;
		//���� ����
		String title = "ȸ������ ������ȣ �߼� �����Դϴ�.";
		//���� ����
		String content = "����ڰ� �������� Ȯ���ϴ� Ȯ�� �ڵ带 �Է��ϼ���."
				+"<br><br>"
				+"������ȣ��" + checkNum + "�Դϴ�";
		
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
