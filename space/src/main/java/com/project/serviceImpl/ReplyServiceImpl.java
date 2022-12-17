package com.project.serviceImpl;

import org.springframework.stereotype.Service;

import com.project.domain.Criteria;
import com.project.domain.Reply;
import com.project.domain.ReplyPage;
import com.project.repository.ReplyMapper;
import com.project.service.ReplyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{
	
	private final ReplyMapper mapper;

//	============================ 댓글 등록 ============================	
	@Override
	public boolean insertReply(Reply reply) {
		return 1 == mapper.insertReply(reply);
	}

//	============================ 댓글 페이지 리스트 ============================
	@Override
	public ReplyPage getReplyList(Criteria cri, String boardnumber) {
		return new ReplyPage(mapper.getReplyTotal(boardnumber), mapper.getReplyList(cri, boardnumber));
	}
	
//	============================ 댓글 삭제 ============================
	@Override
	public boolean replyRemove(String replynumber) {
		return 1 == mapper.replyRemove(replynumber);
	}
	
//	============================ 댓글 수정 ============================
	@Override
	public boolean replyUpdate(Reply reply) {
		return 1 == mapper.replyUpdate(reply);
	}
}
