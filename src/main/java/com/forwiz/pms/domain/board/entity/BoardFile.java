package com.forwiz.pms.domain.board.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.forwiz.pms.domain.file.entity.FileInfo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name="BOARDFILE_SEQ_GENERATOR", sequenceName = "BOARDFILE_SEQ", allocationSize = 1)
public class BoardFile {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARDFILE_SEQ_GENERATOR")
	@Column(name = "board_file_id")
	private Long id; // 번호

	private Long boardId;
	private String delYn;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "file_info_id")
	private FileInfo fileInfo;

	@Builder
	public BoardFile(Long boardId, Long fileId, String delYn, FileInfo fileInfo) {
		this.boardId = boardId;
		this.delYn = "N";
		this.fileInfo = fileInfo;
	}

	public BoardFile delete(String delYn) {
		this.delYn = delYn;
		return this;
	}
}