package com.forwiz.pms.domain.board.dto;

import java.time.LocalDateTime;

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
	private String userOrganization;
	//private List<MultipartFile> multipartFile;
	
	
	@Builder
	public BoardResponseDto(Long id, String title, Category category, String content, String userName,
			LocalDateTime regDate, String userId, String boardScope, String userOrganization) {
		this.id = id;
		this.title = title;
		this.category = category;
		this.content = content;
		this.userName = userName;
		this.regDate = regDate;
		this.userId = userId;
		this.boardScope = boardScope;
		this.userOrganization = userOrganization;
	}
	
	// querydsl Select 대상을 지정
	@QueryProjection
	public BoardResponseDto(Long id, String title, Category category, String content, LocalDateTime regDate, Long viewCount,
			String userName, String userOrganization) {
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
