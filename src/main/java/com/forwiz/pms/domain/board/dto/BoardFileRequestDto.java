package com.forwiz.pms.domain.board.dto;

import com.forwiz.pms.domain.board.entity.BoardFile;
import com.forwiz.pms.domain.file.entity.FileInfo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardFileRequestDto {
	private Long boardFileId;
	private Long id;
	private Long fileId;
	private Long boardId;
	private String originFileName;
	private Long size;
	private String extension;
	
	public BoardFile toEntity(FileInfo fileInfo) {
		return BoardFile
				.builder()
				.boardId(boardId)
				.fileInfo(fileInfo)
				.build();
	}
	
	@Builder
	public BoardFileRequestDto(Long boardId) {
		this.boardId = boardId;
	}
}
