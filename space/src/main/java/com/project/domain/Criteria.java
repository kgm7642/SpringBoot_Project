package com.project.domain;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;

@Data
public class Criteria {
	private int pageNum;
	private int amount;
	private String skill;
	
	public Criteria() {
		this(1, 30, "");
	}
	
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	public Criteria(int pageNum, int amount, String skill) {
		this.pageNum = pageNum;
		this.amount = amount;
		this.skill = skill;
	}
	
	public String getListLink() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
				.queryParam("pageNum", this.pageNum)
				.queryParam("amount", this.amount);
		return builder.toUriString();
	}
	
	public void setSelectArry(String[] selectArry) {
		for(String x : selectArry) {
			if(x.equals("nothing")) {
				this.skill = " ";
			} else if(x!=null) this.skill += x + ",";
		}
		skill = skill.substring(0, skill.length() - 1);
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String[] getSelectArry() {
		return skill == null ? new String[] {} : this.skill.split(",");
	}
}
