package com.forwiz.pms.domain.board.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.forwiz.pms.domain.board.entity.Category;
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
	//private List<MultipartFile> multipartFile;
	
	
	@Builder
	public BoardResponseDto(Long id, String title, Category category, String content, String userName, LocalDateTime regDate, String userId, String boardScope) {
		this.id = id;
		this.title = title;
		this.category = category;
		this.content = content;
		this.userName = userName;
		this.regDate = regDate;
		this.userId = userId;
		this.boardScope = boardScope;
	}
	
	// querydsl Select 대상을 지정
	@QueryProjection
	public BoardResponseDto(Long id, String title, Category category, String content, LocalDateTime regDate, Long viewCount,
			String userName) {
		this.id = id;
		this.title = title;
		this.category = category;
		this.content = content;
		this.regDate = regDate;
		this.viewCount = viewCount;
		this.userName = userName;
	}

	public BoardResponseDto() {
		
	}

	//게시판 목록 조회 subselect
	public BoardResponseDto(BoardSubSelect boardSubSelect) {
		this.id = boardSubSelect.getBoardId();
		this.title = boardSubSelect.getTitle();
		this.category = boardSubSelect.getCategory();
		this.regDate = boardSubSelect.getRegDate();
		this.viewCount = boardSubSelect.getViewCount();
		this.userName = boardSubSelect.getUserName();
	}
}
