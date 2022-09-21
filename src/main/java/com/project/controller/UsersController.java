package com.project.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.domain.PrincipalDetails;
import com.project.domain.Skill;
import com.project.domain.Users;
import com.project.domain.UsersJoin;
import com.project.service.UsersService;

import lombok.RequiredArgsConstructor;


//스프링 시큐리티는 기본 세션말고도 시큐리티가 관리하는 세션이 존재한다.
//이 세션에 들어갈 수 있는 타입은 Authentication 객체뿐이다.
//Authentication에는 UserDetails와 OAuth2User 타입이 들어갈 수 있다.
//UserDetails 타입은 일반 로그인, OAuth2User 타입은 간편 로그인 일 경우

@Controller
@RequiredArgsConstructor
public class UsersController {

	private final UsersService service;
	
	@GetMapping("/test/login")
	public @ResponseBody String loginTest(
			Authentication authentication,
			@AuthenticationPrincipal PrincipalDetails userDetails) { //DI(의존성 주입)
		System.out.println("/test/login =============");
		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		System.out.println("authentication : " + principalDetails.getUsers());
		System.out.println("userDetails : " + userDetails.getUsers());
		return "세션 정보 확인하기";
	}
	
	@GetMapping("/test/oauth/login")
	public @ResponseBody String testOAuthLogin(
			Authentication authentication,
			@AuthenticationPrincipal OAuth2User oauth) { //DI(의존성 주입)
		System.out.println("/test/oauth/login =============");
		OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
		System.out.println("authentication : " + oauth2User.getAttributes());
		System.out.println("oauth2User : " + oauth.getAttributes());
		
		return "OAuth 세션 정보 확인하기";
	}
	
	
	@GetMapping("/login")
	public String login() {
		return "/users/login";
	}
	
	@GetMapping("/loginNickname")
	public String loginNickname(HttpServletRequest request, Model model) {
		Users users = (Users) request.getSession().getAttribute("users");
		System.out.println("loginNickname"+users);
		if(users.getUsersnickname()==null) {
//			닉네임이 null임(처음 접속한 유저)
			model.addAttribute("users", users);
			return "/users/loginNickname";
		} else {
			return "redirect:/";
		}
	}
	
	@GetMapping("/loginSkill")
	public String loginSkill() {
		return "redirect:/";
	}
	
	@PostMapping("/loginSkill")
	public String loginSkill(HttpServletRequest request, Model model, Users users) {
		if(users.getSkill()!=null) {
			return "redirect:/";
		}
		model.addAttribute("skillList", service.skill());
		model.addAttribute("users", users);
		return "/users/loginSkill";

	}
	
	@PostMapping("/loginFinish")
	public String loginFinish(Users users) {
		System.out.println("스킬 입력까지 끝난 유저 정보"+users);
		System.out.println(users.getSkill());
		service.joinOAuth(users);
		return "redirect:/";
	}
	
	@GetMapping("/join")
	public String join() {
		return "/users/join";
	}
	
	@PostMapping("/joinDo")
	public String joinDo(Users users) {
		service.join(users);
		return "redirect:/login";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
	    new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
	    return "redirect:/";
	  }

//	OAuth 로그인을 해도 PrincipalDetails로 받을 수 있고
//	일반 로그인을 해도 PrincipalDetails로 받을 수 있다.
	@GetMapping("/user")
	public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		System.out.println("principalDetails : " + principalDetails.getUsers());
		return "user";
	}
	
	@GetMapping("/admin")
	public String admin() {
		return "admin/admin";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/info")
	public @ResponseBody String info() {
		return "개인정보";
	}
	
	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@GetMapping("/data")
	public @ResponseBody String data() {
		return "개인정보";
	}
}
