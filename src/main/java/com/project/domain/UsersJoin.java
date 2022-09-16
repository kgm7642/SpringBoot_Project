package com.project.domain;

import lombok.Data;

@Data
public class UsersJoin {

	private String usersnumber; //DB에서 PK 값
	private String usersid; // 로그인용 ID 값
	private String userspw; // 비밀번호
	private String usersnickname; // 닉네임
	private String usersemail; // 이메일
    private boolean emailVerified;	//이메일 인증 여부
	private boolean userslock; // 계정 잠김 여부
	private String role; // 유저 역할
	private String createdate; // 계정 생성 날짜
	private String modifydate; // 계정 수정 날짜
	
}
