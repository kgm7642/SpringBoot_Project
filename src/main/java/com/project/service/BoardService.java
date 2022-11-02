package com.project.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.project.domain.Board;
import com.project.domain.BoardSaveForm;
import com.project.domain.BoardView;
import com.project.domain.Criteria;
import com.project.repository.BoardMapper;

import ch.qos.logback.core.util.SystemInfo;
import lombok.RequiredArgsConstructor;

public interface BoardService {
	
	public ArrayList<BoardView> boardList(Criteria cri);

	public int getTotal(Criteria cri);
	
	public BoardView getBoard(String boardnumber);
	
	public void writeBoard(BoardSaveForm boardSaveForm);

	public void updateViewCnt(String boardnumber);

	public Board getBoardDetail(String boardnumber);
	
	public boolean updateBoard(Board board);
	
}
