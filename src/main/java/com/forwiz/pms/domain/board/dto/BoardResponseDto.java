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
	private LocalDateTime uptDate;
	private Long viewCount;
	private String userName;
	private List<MultipartFile> multipartFile;
	
	
	@Builder
	public BoardResponseDto(Long id, String title, Category category, String content, String userName, LocalDateTime regDate) {
		this.id = id;
		this.title = title;
		this.category = category;
		this.content = content;
		this.userName = userName;
		this.regDate = regDate;
	}
	
	// querydsl Select 대상을 지정
	@QueryProjection
	public BoardResponseDto(Long id, String title, Category category, String content, LocalDateTime regDate, LocalDateTime uptDate, Long viewCount,
			String userName) {
		this.id = id;
		this.title = title;
		this.category = category;
		this.content = content;
		this.regDate = regDate;
		this.uptDate = uptDate;
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