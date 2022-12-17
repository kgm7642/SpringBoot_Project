package com.project.domain;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Smarteditor {
	private MultipartFile filedata;
	private String callback;
	private String callback_func;
}
