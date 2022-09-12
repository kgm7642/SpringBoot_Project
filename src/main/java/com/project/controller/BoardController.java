package com.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.domain.Criteria;
import com.project.domain.Page;
import com.project.service.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService service;
	
	@GetMapping("/list")
	public String boardList(Model model, Criteria cri) {
		model.addAttribute("boardList", service.boardList(cri));
		model.addAttribute("pageMaker", new Page(service.getTotal(cri), cri));
		return "/board/boardList";
	}
	
	@GetMapping("/view")
	public String boardView(Model model, int boardnumber) {
		model.addAttribute("board",service.getBoard(boardnumber));
		return "/board/boardView";
	}
}
