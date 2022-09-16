package com.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.domain.PrincipalDetails;
import com.project.domain.Users;
import com.project.domain.UsersJoin;
import com.project.repository.UsersMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService{

	private final UsersMapper mapper;
	
	@Transactional
	public int login(Users users) {
		return mapper.login(users);
	}
	
	@Transactional
	public void join(Users users) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		users.setUserspw(encoder.encode(users.getUserspw()));
		users.setRole(users.getRole());
		mapper.join(users);
	}
	
	@Transactional
	public void joinOAuth(Users users) {
		mapper.joinOAuth(users);
	}
	
	@Transactional
	public Users getUsers(String userid) {
		return mapper.getUsers(userid);
	}
		
	@Override
	public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
		Users users =  mapper.getUsers(userid);
		System.out.println("=== UserDetails ===");
		if(users != null) {			
			return new PrincipalDetails(users);
		}
		return null;
	}
}
