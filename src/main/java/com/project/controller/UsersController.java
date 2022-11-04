package com.project.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.project.domain.PrincipalDetails;
import com.project.domain.Skill;
import com.project.domain.Users;
import com.project.domain.GoogleUsers;
import com.project.service.UsersService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


//스프링 시큐리티는 기본 세션말고도 시큐리티가 관리하는 세션이 존재한다.
//이 세션에 들어갈 수 있는 타입은 Authentication 객체뿐이다.
//Authentication에는 UserDetails와 OAuth2User 타입이 들어갈 수 있다.
//UserDetails 타입은 일반 로그인, OAuth2User 타입은 간편 로그인 일 경우

@Slf4j
@Controller
@RequiredArgsConstructor
public class UsersController {

	@Value("${file.dir}")
	private String fileDir;
	
	private final UsersService usersService;
	
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
		System.out.println("세션확인"+request.getSession().getAttribute("users"));
		if(request.getSession().getAttribute("users") == null || request.getSession().getAttribute("users").equals("not")) {
//			로그인 없이 loginNickname 주소로 다이렉트로 접속한 경우
			return "redirect:/";
		}  else {
			if( ((Users) request.getSession().getAttribute("users")).getUsersnickname() == null ) {				
				
//			닉네임이 null임(처음 접속한 유저)
				model.addAttribute("users", request.getSession().getAttribute("users"));
				return "/users/loginNickname";
			} else {
				return "redirect:/";
			}
		}
	}
	
	@ResponseBody
	@PostMapping(value="checkNick",  consumes = "application/json")
	public ResponseEntity<String> checkNick(@RequestBody Users users){
		boolean check = usersService.checkNick(users);
		log.info("닉네임 중복 체크 성공");
		return check ? new ResponseEntity<String>("fail",HttpStatus.OK) : new ResponseEntity<String>("success",HttpStatus.OK);
	}
	
	@GetMapping("/loginSkill")
	public String loginSkill() {
		return "redirect:/";
	}
	
	@PostMapping("/loginSkill")
	public String loginSkill(HttpServletRequest request, Model model, Users users) {
		System.out.println("유저 확인 : " + users);
		if(users.getSkill()!=null) {
			return "redirect:/";
		}
		model.addAttribute("skillList", usersService.skill());
		model.addAttribute("users", users);
		return "/users/loginSkill";

	}
	
	
	@PostMapping("/loginFinish")
	public String loginFinish(Users users) {		
		usersService.joinOAuth(users);
		return "redirect:/";
	}
	
	@GetMapping("/join")
	public String join() {
		return "/users/join";
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
	
	public String getFullPath(Users users, String orgName) {
		int idx = orgName.indexOf(".");
		String imgName = orgName.substring(idx);
		String fullPath = fileDir + users.getProviderid() + imgName;
		users.setImagename(users.getProviderid() + imgName);
		return fullPath;
	}
}
