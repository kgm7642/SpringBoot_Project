package com.project.domain;

import lombok.Data;

@Data
public class JoinUsers {
	private String usersnumber; //DB에서 PK 값
	private String usersnickname; // 닉네임
	private String modifydate; // 계정 수정 날짜
	private String[] skillarry;	// 기술 스택
	private String skill;
	private String imagename; // 이미지 파일 이름
}
