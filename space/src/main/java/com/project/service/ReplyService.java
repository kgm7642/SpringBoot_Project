package com.project.service;

import com.project.domain.Criteria;
import com.project.domain.Reply;
import com.project.domain.ReplyPage;

public interface ReplyService {
	
//	댓글 등록
	public boolean insertReply(Reply reply);
	
//	댓글 페이지 리스트
	public ReplyPage getReplyList(Criteria cri, String boardnumber);
	
//	댓글 삭제
	public boolean replyRemove(String replynumber);
	
//	댓글 수정
	public boolean replyUpdate(Reply reply);
}
