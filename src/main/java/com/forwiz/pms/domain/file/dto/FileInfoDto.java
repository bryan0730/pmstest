package com.forwiz.pms.domain.file.dto;

import com.forwiz.pms.domain.file.entity.FileInfo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FileInfoDto {

	private Long id;	//id
	private String originFileName;	//원본 파일 명
	private String savedFileName;	//저장된 파일 명
	private String uploadDir;	//경로명
	private String extension;	//확장자
	private Long fileVolume;	//파일 크기
	private String contentType;	//contentType
	
	@Builder
	public FileInfoDto(Long id, String originFileName, String savedFileName, String uploadDir, String extension, Long fileVolume,
			String contentType) {
		this.id = id;
		this.originFileName = originFileName;
		this.savedFileName = savedFileName;
		this.uploadDir = uploadDir;
		this.extension = extension;
		this.fileVolume = fileVolume;
		this.contentType = contentType;
	}
	
	//dto >> entity
	public FileInfo toEntity() {
		return FileInfo.builder()
				.originFileName(originFileName)
				.savedFileName(savedFileName)
				.uploadDir(uploadDir)
				.extension(extension)
				.fileVolume(fileVolume)
				.contentType(contentType)
				.build();
	}
}
