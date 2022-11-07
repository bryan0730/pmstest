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
	private String username;
	private List<MultipartFile> multipartFile;

	@Builder
	public BoardResponseDto(Long id, String title, Category category, String content) {
		this.id = id;
		this.title = title;
		this.category = category;
		this.content = content;
	}
	
	// querydsl Select 대상을 지정
	@QueryProjection
	public BoardResponseDto(Long id, String title, Category category, String content, LocalDateTime regDate, LocalDateTime uptDate, Long viewCount,
			String username) {
		this.id = id;
		this.title = title;
		this.category = category;
		this.content = content;
		this.regDate = regDate;
		this.uptDate = uptDate;
		this.viewCount = viewCount;
		this.username = username;
	}

	public BoardResponseDto() {
		
	}
}
