package com.project.repository;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.project.domain.Board;
import com.project.domain.Criteria;



@Mapper
public interface BoardMapper {
	public ArrayList<Board> boardList(Criteria cri);
	public int getTotal(Criteria cri);

	public Board getBoard(int boardnumber);
	
	public void writeBoard(Board board);

}
