package com.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public void join(UsersJoin usersJoin) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		usersJoin.setUserspw(encoder.encode(usersJoin.getUserspw()));
		usersJoin.setAuth(usersJoin.getAuth());
		mapper.join(usersJoin);
	}
		
	@Override
	public Users loadUserByUsername(String userid) throws UsernameNotFoundException {
		Users users =  mapper.getUsers(userid);
		System.out.println("=== UserDetails ===");
		if(users != null) {			
			return users;
		}
		return null;
	}
}
