package com.project.serviceImpl;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.domain.PrincipalDetails;
import com.project.domain.Skill;
import com.project.domain.Users;
import com.project.repository.UsersMapper;
import com.project.service.UsersService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UserDetailsService, UsersService{

	private final UsersMapper mapper;
	
	@Override
	@Transactional
	public void joinOAuth(Users users) {		
		String skill = "";
		if(users.getSkillarry()==null) {
			users.setSkill("선택안함");
		} else {			
			for(int i=0; i<users.getSkillarry().length; i++) {
				skill+=users.getSkillarry()[i]+",";
			}
			users.setSkill(skill.substring(0, skill.length()-1));
		}
		mapper.joinOAuth(users);
	}
	
	@Override
	@Transactional
	public Users getUsers(String userid) {
		return mapper.getUsers(userid);
	}
	
	@Override
	@Transactional
	public ArrayList<Skill> skill() {
		return mapper.skill();
	}
		
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
		Users users =  mapper.getUsers(userid);
		System.out.println("=== UserDetails ===");
		if(users != null) {			
			return new PrincipalDetails(users);
		}
		return null;
	}

	@Override
	@Transactional
	public boolean checkNick(Users users) {
		return 1 == mapper.checkNick(users);
	}
}
