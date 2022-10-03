package com.project.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.project.config.oauth.PrincipalOauth2UserService;
import com.project.service.UsersService;

import lombok.RequiredArgsConstructor;

// 1. 코드받기(인증), 2. 액세스토큰(권한),
// 3. 사용자프로필 정보를 가져오고 4-1. 그 정보를 토대로 회원가입을 자동으로 진행시키기도함
// 4-2. (이메일, 전화번호, 이름, 아이디) 쇼핑몰 -> (집주소), 백화점몰 -> (VIP등급, 일반등급)

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
							//secured 어노테이션 활성화, preAuthorize, postAuthorize 어노테이션 활성화
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final UsersService userService;	
	private final PrincipalOauth2UserService principalOauth2UserService;
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers()
				.frameOptions().sameOrigin();
		
		http.cors().and().csrf().disable();
		
		http.authorizeRequests()
				.antMatchers("/user/**").authenticated()
				.antMatchers("/board/**").authenticated()
				.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
				.anyRequest().permitAll()
				.and()
				.formLogin()
				.successHandler(new LoginSuccessHandler(userService))
				.loginPage("/login") // 로그인 페이지 주소
				.loginProcessingUrl("/loginDo") // 로그인 확인을 실행 할 주소
				.defaultSuccessUrl("/") // 로그인 성공 후 처음 접
				.and()
				.oauth2Login()
				.successHandler(new LoginSuccessHandler(userService))
				.loginPage("/login") // 로그인 페이지 주소
				.userInfoEndpoint()
				.userService(principalOauth2UserService); // 구글 로그인이 완료된 뒤의 후처리가 필요함. Tip. 코드x, (액세스토큰 + 사용자프로필정보 O)
//				.and()
//				.logout()
//				.logoutSuccessUrl("/") // 로그아웃 성공시 리다이렉트 할 주소
//				.invalidateHttpSession(true) // 세션 날리기
//				.permitAll();
		}
	
	  @Override
	  public void configure(AuthenticationManagerBuilder auth) throws Exception { // 9
	    auth.userDetailsService(userService)
	    	// 해당 서비스(userService)에서는 UserDetailsService를 implements해서 
	        // loadUserByUsername() 구현해야함 (서비스 참고)
	    	.passwordEncoder(new BCryptPasswordEncoder()); 
	  }
}