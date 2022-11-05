package com.project.domain;

import lombok.Data;

@Data
public class File {
	private String systemname; // 파일이 저장될 때 이름
	private String orgname; // 파일의 맨 처음 이름
	private String username; // 이 파일을 저장한 유저의 아이디
}
