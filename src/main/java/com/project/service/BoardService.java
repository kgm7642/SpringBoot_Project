package com.project.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.project.domain.Board;
import com.project.domain.Criteria;
import com.project.repository.BoardMapper;

import ch.qos.logback.core.util.SystemInfo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardMapper mapper;
	
	public ArrayList<Board> boardList(Criteria cri){
		return mapper.boardList(cri);
	}

	public int getTotal(Criteria cri) {
		return mapper.getTotal(cri);
	}
	
	public Board getBoard(int boardnumber) {
		return mapper.getBoard(boardnumber);
	}

}
