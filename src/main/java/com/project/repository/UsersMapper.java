package com.project.repository;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.project.domain.Skill;
import com.project.domain.Users;
import com.project.domain.UsersJoin;


@Mapper
public interface UsersMapper {

	public int login(Users users);
	public Users getUsers(String userid);
	public void join(Users users);
	public void joinOAuth(Users users);
	public ArrayList<Skill> skill();

}
