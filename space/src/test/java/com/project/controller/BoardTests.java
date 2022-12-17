package com.project.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.project.service.BoardService;

@SpringBootTest
public class BoardTests {

	@Autowired
	private BoardService boardService;
	
	@Test
	@DisplayName("게시글 상세보기")
	@Transactional
	public void 게시판_상세보기_테스트() throws Exception {
		assertNotNull(boardService.getBoard("1"));
	}
}
