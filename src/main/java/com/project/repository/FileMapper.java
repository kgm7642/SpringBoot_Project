package com.project.repository;

import java.util.LinkedList;

import org.apache.ibatis.annotations.Mapper;

import com.project.domain.Item;


@Mapper
public interface FileMapper {
	public void saveFile(Item item);
//	public Item findAttach(String boardNumber);
//	public LinkedList<Item> findImages(String boardNumber);
	public Item getFile(String usersnumber);
	public String getstorefilename(String usersnumber);
}