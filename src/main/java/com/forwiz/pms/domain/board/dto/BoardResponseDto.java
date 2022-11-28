package com.forwiz.pms.domain.board.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.forwiz.pms.domain.board.entity.Board;
import com.forwiz.pms.domain.board.entity.Category;
import com.forwiz.pms.domain.reply.dto.ReplyResponse;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

/*Board에서 값을 가져올때 사용*/
@Getter
public class BoardResponseDto {

	private Long id;
	private String title;
	private Category category;
	private String content;
	private LocalDateTime regDate;
	private Long viewCount;
	private String userName;
	private String userId;
	private String boardScope;
	private String userOrganization;
	private List<ReplyResponse> replies;

	@Builder
	public BoardResponseDto(Board board) {
		this.id = board.getId();
		this.title = board.getTitle();
		this.category = board.getCategory();
		this.content = board.getContent();
		this.regDate = board.getRegDate();
		this.boardScope = board.getBoardScope();
		this.userName = board.getPmsUser().getUserName();
		this.userId = board.getPmsUser().getUserId();
		this.userOrganization = board.getPmsUser().getUserOrganizationName();
		this.replies = board.getReplies().stream().map(ReplyResponse::new).collect(Collectors.toList());
	}

	// querydsl Select 대상을 지정
	@QueryProjection
	public BoardResponseDto(Long id, String title, Category category, String content, LocalDateTime regDate,
			Long viewCount, String userName, String userOrganization) {
		this.id = id;
		this.title = title;
		this.category = category;
		this.content = content;
		this.regDate = regDate;
		this.viewCount = viewCount;
		this.userName = userName;
		this.userOrganization = userOrganization;
	}

	public BoardResponseDto() {

	}
}
