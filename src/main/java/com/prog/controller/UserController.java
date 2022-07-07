package com.prog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.prog.entity.UserDtls;

@Controller
public class UserController {
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@PostMapping("/registr")
	public String register(@ModelAttribute UserDtls u){
		
		System.out.println(u);
		return "/redirect";
	}
	
}
