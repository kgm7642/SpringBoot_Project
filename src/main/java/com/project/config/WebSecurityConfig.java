package com.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.service.UsersService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final UsersService userService;	
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/", "/home", "/users/join", "/users/logout", "/users/joinDo").permitAll()
	            .antMatchers("/").hasRole("USER") // USER, ADMIN만 접근 가능
	            .antMatchers("/users/admin").hasRole("ADMIN") // ADMIN만 접근 가능
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/users/login") // 로그인 페이지 주소
				.loginProcessingUrl("/users/loginDo") // 로그인 확인을 실행 할 주소
				.defaultSuccessUrl("/") // 로그인 성공 후 리다이렉트 할 주소
				.permitAll()
				.and()
			.logout()
				.logoutSuccessUrl("/") // 로그아웃 성공시 리다이렉트 할 주소
				.invalidateHttpSession(true) // 세션 날리기
				.permitAll();
	}
	
	  @Override
	  public void configure(AuthenticationManagerBuilder auth) throws Exception { // 9
	    auth.userDetailsService(userService)
	    	// 해당 서비스(userService)에서는 UserDetailsService를 implements해서 
	        // loadUserByUsername() 구현해야함 (서비스 참고)
	    	.passwordEncoder(new BCryptPasswordEncoder()); 
	   }
}