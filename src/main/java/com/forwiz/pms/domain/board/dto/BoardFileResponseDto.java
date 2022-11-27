package com.forwiz.pms.domain.board.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class BoardFileResponseDto {
	private Long boardFileId;

	private Long fileId;

	private String originFileName;

	private Long size;

	private String extension;
	
	private String delYn;

	public BoardFileResponseDto() {

	}

	@QueryProjection
	public BoardFileResponseDto(Long boardFileId, Long fileId, String originFileName, Long size, String extension, String delYn) {
		this.boardFileId = boardFileId;
		this.fileId = fileId;
		this.originFileName = originFileName;
		this.size = size;
		this.extension = extension;
		this.delYn = delYn;
	}
}
