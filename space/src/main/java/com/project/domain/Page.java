package com.project.domain;

import java.util.ArrayList;

import lombok.Data;

@Data
public class Page {

	private int startPage;
	private int endPage;
	private int realEndPage;
	private int amount;
	private boolean prev;
	private boolean next;
	private int total;
	private Criteria cri;
	private ArrayList<Integer> pages;
	private ArrayList<BoardView> boardList;
	
	public Page(int total, Criteria cri, ArrayList<BoardView> boardList) {
		this.total = total;
		this.cri = cri;
		this.amount = 30;
		this.pages = new ArrayList<Integer>();
		
		this.endPage = (int)Math.ceil(cri.getPageNum()/10.0)*amount;
		this.startPage = endPage - amount + 1;
		this.realEndPage = (int)Math.ceil((total*1.0)/10);
		
		if(endPage > realEndPage) endPage = realEndPage;
		this.prev = this.startPage > 1;
		this.next = this.endPage < this.realEndPage;
		for(int i=startPage; i<endPage+1; i++) {
			pages.add(i);
		}
		
		this.boardList = boardList;
	}
}
