package com.project.service;

import java.util.LinkedList;

import javax.validation.constraints.NotNull;

import com.project.domain.Item;
import com.project.domain.SaveFile;

public interface FileService {
	public void saveFile(SaveFile file);
//	public Item findAttach(String boardNumber);
//	public LinkedList<Item> findImages(String boardNumber);

	public Item getFile(String usersnumber);

	public String getStorefilename(String usersnumber);
}
