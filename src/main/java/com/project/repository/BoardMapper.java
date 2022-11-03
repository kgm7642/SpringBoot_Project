package com.project.repository;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.project.domain.Board;
import com.project.domain.BoardSaveForm;
import com.project.domain.BoardView;
import com.project.domain.Criteria;



@Mapper
public interface BoardMapper {
	
	// 게시글 리스트
	public ArrayList<BoardView> boardList(Criteria cri);
	
	// 전체 게시글 수
	public int getTotal(Criteria cri);

	// 게시글 상세보기(view 에서 사용)
	public BoardView getBoard(String boardnumber);
	
	// 게시글 작성 
	public void writeBoard(BoardSaveForm boardSaveForm);
	
	//
	public void updateViewCnt(String boardnumber);

	// 게시글 상세보기( 기본 board 객체)
	Board getBoardDetail(String boardnumber);
	
	// 게시글 수정
	int updateBoard(Board board);
	
	// 게시글 삭제
	int removeBoard(String boardnum);
	
	// 게시글 번호에 맞는 댓글 검색
	int searchBoardReply(String boardnum);
	
	// 댓글 모두 삭제
	int removeBoardReply(String boardnum);
}
