package com.project.repository;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.project.domain.Skill;
import com.project.domain.UpdateUsers;
import com.project.domain.Users;
import com.project.domain.GoogleUsers;


@Mapper
public interface UsersMapper {

	public Users getUsers(String userid);
	public void joinOAuth(Users users);
	public ArrayList<Skill> skill();
	public int checkNick(Users users);
	public void updateInfo(UpdateUsers updateUsers);
	public void fire(String usersnumber);
	
}
