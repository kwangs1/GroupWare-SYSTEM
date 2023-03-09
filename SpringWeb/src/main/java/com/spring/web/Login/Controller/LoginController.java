package com.spring.web.Login.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.spring.web.Login.Service.LoginService;

@Controller
public class LoginController {
	@Autowired
	private LoginService service;
	
	@GetMapping("/login")
	public void login() {
		
	}
	
}
