package com.forwiz.pms.domain.reply.dto;

import java.time.LocalDateTime;

import com.forwiz.pms.domain.reply.entity.Reply;

import lombok.Getter;

@Getter
public class ReplyResponse {
	private Long id;
	private String content;
	private String replierName;
	private Long boardId;
	private LocalDateTime regDate;
	
	/* Entity To Dto */
	public ReplyResponse(Reply reply) {
		this.id = reply.getId();
		this.content = reply.getContent();
		this.replierName = reply.getReplier().getUserName();
		this.boardId = reply.getBoard().getId();
		this.regDate = reply.getRegDate();
	}
}
