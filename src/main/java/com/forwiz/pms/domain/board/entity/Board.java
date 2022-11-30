package com.forwiz.pms.domain.board.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.forwiz.pms.domain.board.dto.BoardRequestDto;
import com.forwiz.pms.domain.message.entity.MessageFile;
import com.forwiz.pms.domain.reply.entity.Reply;
import com.forwiz.pms.domain.user.entity.PmsUser;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "BOARD_SEQ_GENERATOR", sequenceName = "BOARD_SEQ", allocationSize = 1)
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARD_SEQ_GENERATOR")
	@Column(name = "board_id")
	private Long id; // 번호

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Category category;// 카테고리 [NOTICE, WORK]

	@Column(length = 200, nullable = false)
	private String title; // 제목

	@Column(length = 5000)
	private String content; // 내용

	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime regDate; // 등록 날짜

	@Column(nullable = false)
	private Long viewCount; // 조회수

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private PmsUser pmsUser; // 작성자

	@Column(nullable = false)
	private String boardScope; // 공개범위

	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("reply_id asc")
	private List<Reply> replies = new ArrayList<>();

	public Board update(BoardRequestDto boardRequestDto) {
		this.title = boardRequestDto.getTitle();
		this.category = boardRequestDto.getCategory();
		this.content = boardRequestDto.getContent();
		this.boardScope = boardRequestDto.getBoardScope();
		return this;
	}

	@Builder
	public Board(String title, Category category, String content, PmsUser pmsUser, String boardScope) {
		this.title = title;
		this.category = category;
		this.content = content;
		this.viewCount = 0L;
		this.pmsUser = pmsUser;
		this.boardScope = boardScope;
	}

	public Board updateViewCount(Long viewCount) {
		this.viewCount = viewCount + 1;
		return this;
	}

}