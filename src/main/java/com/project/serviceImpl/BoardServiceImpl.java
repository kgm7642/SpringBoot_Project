package com.project.serviceImpl;

import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.domain.Board;
import com.project.domain.BoardSaveForm;
import com.project.domain.BoardView;
import com.project.domain.Board_Skill;
import com.project.domain.Criteria;
import com.project.repository.BoardMapper;
import com.project.service.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

	private final BoardMapper mapper;
	
	@Transactional
	@Override
	public ArrayList<BoardView> boardList(Criteria cri){
		return mapper.boardList(cri);
	}

	@Transactional
	@Override
	public int getTotal(Criteria cri) {
		return mapper.getTotal(cri);
	}
	
	@Transactional
	@Override
	public BoardView getBoard(String boardnumber) {
		return mapper.getBoard(boardnumber);
	}
	
	@Transactional
	@Override
	public void writeBoard(BoardSaveForm boardSaveForm) {
		if(boardSaveForm.getBoardcommunication().equals("email")) {			
			boardSaveForm.setBoardway("mailto:"+boardSaveForm.getBoardway()); 
		} else if(boardSaveForm.getBoardcommunication().equals("phone")) {
			boardSaveForm.setBoardway("tel:"+boardSaveForm.getBoardway());
		}
		mapper.writeBoard(boardSaveForm);
	}

	@Transactional
	@Override
	public void updateViewCnt(String boardnumber) {
		mapper.updateViewCnt(boardnumber);		
	}
	
	@Transactional
	@Override
	public Board getBoardDetail(String boardnumber) {
		Board board = mapper.getBoardDetail(boardnumber);
		if(board.getBoardcommunication().equals("email")) {
			board.setBoardway(board.getBoardway().substring(7));
		} else if(board.getBoardcommunication().equals("phone")) {
			board.setBoardway(board.getBoardway().substring(3));
		}
		return board;
	}
	
	@Transactional
	@Override
	public boolean updateBoard(Board board) {
		return 1 == mapper.updateBoard(board);
	}
	
	@Transactional
	@Override
	public boolean removeBoard(String boardnumber) {
		boolean check = false;
		if(mapper.searchBoardReply(boardnumber)>0) {
			mapper.removeBoardReply(boardnumber);
		}
		if(mapper.removeBoard(boardnumber)==1) {
			check = true;
		}
		return check;
	}

	@Transactional
	@Override
	public ArrayList<Board_Skill> hotBoardList() {
		return mapper.hotBoardList();
	}
}
