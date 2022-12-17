package com.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.domain.Criteria;
import com.project.domain.Reply;
import com.project.domain.ReplyPage;
import com.project.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {

	private final ReplyService service;
	
	@PostMapping(value = "/regist", consumes = "application/json")
	public ResponseEntity<String> regist(@RequestBody Reply reply){
		boolean check = service.insertReply(reply);
		log.info("댓글 등록 성공");
		return check ? new ResponseEntity<String>("success",HttpStatus.OK) : new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping(value = "/pages/{boardnumber}/{pagenum}",
				produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,	MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<ReplyPage> replyList(@PathVariable("boardnumber") String boardnumber, @PathVariable("pagenum") int pagenum) {
		Criteria cri = new Criteria(pagenum, 5);
		log.info("댓글 페이지 리스트");
		return new ResponseEntity<ReplyPage>(service.getReplyList(cri, boardnumber),HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{replynumber}", produces = MediaType.TEXT_PLAIN_VALUE)
	public  ResponseEntity<String> remove(@PathVariable("replynumber") String replynumber) {
		log.info("댓글 삭제 완료");
		return service.replyRemove(replynumber)?
				new ResponseEntity<String>("success",HttpStatus.OK):
				new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(method = {RequestMethod.PUT,RequestMethod.PATCH}, value = "/{replynum}",
					consumes = "application/json", produces = MediaType.TEXT_PLAIN_VALUE)
	public  ResponseEntity<String> modify(@RequestBody Reply moimreplydto) {
		if(service.replyUpdate(moimreplydto)) {
			log.info("댓글 수정 완료");
			return new ResponseEntity<String>("success",HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
