package com.project.serviceImpl;

import java.util.LinkedList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.domain.Item;
import com.project.domain.SaveFile;
import com.project.repository.FileMapper;
import com.project.service.FileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{
	
	private final FileMapper mapper;
	
	@Override
	@Transactional
	public void saveFile(SaveFile file) { 
		Item item = new Item();
		
		// 첨부파일 저장
		item.setUsersnumber(file.getUsersnumber());
		item.setUploadFileName(file.getSaveFile().getUploadFileName());
		item.setStoreFileName(file.getSaveFile().getStoreFileName());
		mapper.saveFile(item);
	}

	@Override
	public Item getFile(String usersnumber) {
		return mapper.getFile(usersnumber);
	}

//	@Override
//	@Transactional
//	public Item findAttach(String boardNumber) {
//		return mapper.findAttach(boardNumber);
//	}
//	
//	@Override
//	@Transactional
//	public LinkedList<Item> findImages(String boardNumber) {
//		return mapper.findImages(boardNumber);
//	}
}
