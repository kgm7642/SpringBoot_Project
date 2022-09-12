package com.project.repository;

import org.apache.ibatis.annotations.Mapper;

import com.project.domain.Users;
import com.project.domain.UsersJoin;


@Mapper
public interface UsersMapper {

	public int login(Users users);
	public Users getUsers(String userid);
	public void join(UsersJoin usersJoin);

}
