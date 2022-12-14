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
import com.project.domain.Skill;
import com.project.domain.UpdateUsers;
import com.project.domain.Users;
import com.project.domain.GoogleUsers;
import com.project.repository.UsersMapper;

import lombok.RequiredArgsConstructor;

public interface UsersService extends UserDetailsService{

	public void joinOAuth(Users users);
	
	public Users getUsers(String userid);
	
	public ArrayList<Skill> skill();
		
	public UserDetails loadUserByUsername(String userid);

	public boolean checkNick(Users users);
	
	public void updateInfo(Users users);

	public void fire(String username, String usersnickname, String usersnumber);
	
	public int getLastIndex();
}
