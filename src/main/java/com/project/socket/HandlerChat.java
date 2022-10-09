package com.project.socket;

import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


import com.google.gson.Gson;
import com.project.controller.BoardController;
import com.project.domain.MoimChatDTO;
import com.project.domain.UserDTO;
import com.project.service.BoardService;
import com.project.service.MoimService;
import com.project.service.UsersService;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/socket")
@RequiredArgsConstructor
public class HandlerChat extends TextWebSocketHandler {

	private final MoimService service;
	
	private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
	
	// 클라이언트가 서버로 연결 처리
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {

		System.out.println("서버 연결 완료");

		// 채팅방에 접속한 사용자 세션을 리스트에 저장
		sessionList.add(session);
		System.out.println(session);
	}

	// 클라이언트가 서버로 메세지 전송 처리
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("메세지 전송 완료");
		
		Gson gson = new Gson();
		MoimChatDTO msg = gson.fromJson(message.getPayload(), MoimChatDTO.class);
		
		// DB에 채팅 저장한다.
		service.insertMoimChat(msg);
		
		// 저장 후 DB에서 채팅을 가져온다.
		msg = service.getChatLast(""+msg.getMoimnum());
		
		// 뷰단으로 채팅 정보를 보내주기 위해 JSON파일로 변형
		TextMessage sendMsg = new TextMessage(gson.toJson(msg));
		
        //모든 유저에게 메세지 출력
        for(WebSocketSession sess : sessionList){
            sess.sendMessage(sendMsg);
        }
	}

	// 클라이언트가 연결을 끊음 처리
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

		System.out.println("연결 끊김");
		
		// 채팅방에서 퇴장한 사용자 세션을 리스트에서 제거
		sessionList.remove(session);
	}
}
