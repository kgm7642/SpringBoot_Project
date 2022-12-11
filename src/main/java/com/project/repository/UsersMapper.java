package com.project.repository;

import java.util.ArrayList;
import java.util.List;

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
	public void updateInfo(Users users);
	public void fire(String username);
	public List<String> getBoardNumbers(String username);
	public void deleteBoardReply(String usersnickname);
	public void deleteMyBoardReply(String boardnumber);
	public void deleteBoard(String boardnumber);
	public int getLastIndex();
}
