package com.project.domain;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class BoardSaveForm {
	
	private String boardnumber;
	@NotNull
	private String boardwriter;
	@NotNull
	private String boardcategory;
	@NotNull
	private String boardmembers;
	@NotNull
	private String boardmeet;
	@NotNull
	private String boardperiod;
	@NotNull
	private String boardskill;
	@NotNull
	private String boardstart;
	@NotNull
	private String boardcommunication;
	@NotNull
	private String boardway;
	@NotNull
	private String boardtitle;
	@NotNull
	private String boardcontent;
	@NotNull
	private String boardview;
	@NotNull
	private String storefilename;
}
