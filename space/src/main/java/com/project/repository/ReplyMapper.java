package com.project.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.domain.Criteria;
import com.project.domain.Reply;

@Mapper
public interface ReplyMapper {

//	댓글 등록
	int insertReply(Reply reply);
	
//	댓글 리스트
	int getReplyTotal(String boardnumber);
	List<Reply> getReplyList(@Param("cri") Criteria cri,@Param("boardnumber") String boardnumber);

//	댓글 삭제	
	int replyRemove(String replynumber);	
	
//	댓글 수정
	int replyUpdate(Reply reply);
}
