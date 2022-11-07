package com.forwiz.pms.domain.board.entity;

public enum Category {
	NOTICE("공지"), WORK("업무");
	
	private final String description; //"공지" , "업무"
	
	Category(String description){
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
