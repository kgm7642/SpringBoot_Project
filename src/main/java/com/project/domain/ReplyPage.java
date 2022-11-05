package com.project.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReplyPage {

	private int replyCnt;
	private List<Reply> list;
}
