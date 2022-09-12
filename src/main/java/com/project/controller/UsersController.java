package com.project.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.domain.Users;
import com.project.domain.UsersJoin;
import com.project.service.UsersService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

	private final UsersService service;
	
	@GetMapping("/login")
	public String login() {
		return "/users/login";
	}
	
	@GetMapping("/join")
	public String join() {
		return "/users/join";
	}
	
	@PostMapping("/joinDo")
	public String joinDo(UsersJoin usersJoin) {
		service.join(usersJoin);
		return "redirect:/users/login";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
	    new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
	    return "redirect:/";
	  }
	
	@GetMapping("/admin")
	public String admin() {
		return "/users/admin";
	}
}
