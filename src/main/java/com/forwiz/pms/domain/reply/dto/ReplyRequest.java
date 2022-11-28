package com.forwiz.pms.domain.reply.dto;

import com.forwiz.pms.domain.board.entity.Board;
import com.forwiz.pms.domain.reply.entity.Reply;
import com.forwiz.pms.domain.user.entity.PmsUser;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyRequest {
	private Long id;
	private String content;
	private PmsUser replier;
	private Board board;
	
	public Reply toEntity() {
		return Reply.builder()
				.id(id)
				.content(content)
				.replier(replier)
				.board(board)
				.build();
	}
}
