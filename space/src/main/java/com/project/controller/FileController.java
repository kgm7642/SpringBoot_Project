package com.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.springframework.core.io.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import com.project.domain.Item;
import com.project.domain.SaveFile;
import com.project.domain.UploadFile;
import com.project.domain.Users;
import com.project.file.FileStore;
import com.project.service.FileService;
import com.project.service.UsersService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FileController {
	
	private final UsersService usersService;
	private final FileService service;
	private final FileStore fileStore;
	
	 @GetMapping("/file/new")
	 public String newItem(@ModelAttribute Users users) {
		 return "item-form";
	 }
	 
	 
//	 // 보드번호를 가지고 파일 정보들 반환해주는 api
//	 @GetMapping("/file/{boardNumber}")
//	 public ResponseEntity items(@PathVariable String boardNumber, Model model) {
//		 FileReturn fileReturn = new FileReturn();
//		 Item item = service.findAttach(boardNumber);
//		 //model.addAttribute("item", item);
//		 //model.addAttribute("images", service.findImages(boardNumber));
//		 fileReturn.setItem(item);
//		 fileReturn.setItems(service.findImages(boardNumber));
//		 //return "item-view";
//		 return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.TEST_SUCCESS, fileReturn), HttpStatus.OK);
//	 }
	
	 // 이미지파일명으로 이미지 파일 반환(프론트에서 보드에 맞는 이미지파일명 반복문 통해서 보내주면 하나씩 반환해줌)
	 @ResponseBody
	 @GetMapping("/image/{filename}")
	 public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
		 return new UrlResource("file:" + fileStore.getFullPath(filename));
	 }

}
