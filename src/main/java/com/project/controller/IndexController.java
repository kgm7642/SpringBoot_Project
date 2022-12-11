package com.project.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.domain.PrincipalDetails;
import com.project.domain.Users;
import com.project.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController {
	
	private final BoardService boardService;
	
	@GetMapping
	public String index(@RequestParam(required = false) String fire, HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model) {
		// 처음 접속한(로그인 하지 않은) 유저는 session 값이 not 임
		model.addAttribute("hotList", boardService.hotBoardList()); 
		System.out.println("인덱스에 접속했습니다.");
		
		// 로그인 하지 않은 상태
		if(session.getAttribute("users")==null || session.getAttribute("users").equals("not")) {	
			System.out.println("세션확인 처음"+session.getAttribute("users"));
			session.setAttribute("users", "not");
			
		// 로그인을 한 상태
		} else {
			// 회원가입 도중에 인덱스 페이지로 이동한 상태
			if( ((Users) session.getAttribute("users")).getUsersnickname() == null ) {							
				System.out.println("세션확인 닉에서 이동"+session.getAttribute("users"));
					session.setAttribute("users", "not");
//					닉네임을 정하지 않고 메인화면으로 이동시 시큐리티를 통해 로그아웃 시켜줌
					new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
					return "redirect:/";
				}
			}
		return "index";
	}
}
