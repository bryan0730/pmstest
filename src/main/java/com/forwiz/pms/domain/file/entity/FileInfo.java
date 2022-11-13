package com.forwiz.pms.domain.file.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.forwiz.pms.domain.board.entity.BoardFile;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name="FILE_INFO_SEQ_GENERATOR", sequenceName = "FILE_INFO_SEQ", allocationSize = 1)
public class FileInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILE_INFO_SEQ_GENERATOR")
	@Column(name = "file_info_id")
	private Long id; // id

	@Column(nullable = false)
	private String originFileName; // 원본 파일명

	@Column(nullable = false)
	private String savedFileName; // 저장된 파일명

	private String uploadDir; // 경로명

	private String extension; // 확장자

	private Long fileVolume; // 파일 사이즈

	private String contentType; // ContentType

	@CreatedDate
	private LocalDateTime regDate; // 등록 날짜

	@OneToOne(mappedBy = "fileInfo", fetch=FetchType.LAZY)
	private BoardFile boardFile;

	@Builder
	public FileInfo(Long id, String originFileName, String savedFileName, String uploadDir, String extension, Long fileVolume,
			String contentType) {
		this.id = id;
		this.originFileName = originFileName;
		this.savedFileName = savedFileName;
		this.uploadDir = uploadDir;
		this.extension = extension;
		this.fileVolume = fileVolume;
		this.contentType = contentType;
	}
}