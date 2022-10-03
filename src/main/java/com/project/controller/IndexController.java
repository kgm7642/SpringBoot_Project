package com.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.domain.Users;
import com.project.service.UsersService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController {
	
	private final UsersService usersService;
	
	@GetMapping
	public String index(Model model) {
		model.addAttribute("skillList", usersService.skill());
		return "index";
	}
	
}
