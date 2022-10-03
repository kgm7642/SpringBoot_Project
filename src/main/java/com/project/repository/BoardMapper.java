package com.project.repository;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.project.domain.Board;
import com.project.domain.BoardSaveForm;
import com.project.domain.BoardView;
import com.project.domain.Criteria;



@Mapper
public interface BoardMapper {
	public ArrayList<BoardView> boardList(Criteria cri);
	public int getTotal(Criteria cri);

	public BoardView getBoard(int boardnumber);
	
	public void writeBoard(BoardSaveForm boardSaveForm);

}
