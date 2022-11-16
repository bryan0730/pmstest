package com.forwiz.pms.domain.board.entity;

import java.time.LocalDateTime;

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
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

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
@SequenceGenerator(name="BOARD_SEQ_GENERATOR", sequenceName = "BOARD_SEQ", allocationSize = 1)
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARD_SEQ_GENERATOR")
	@Column(name = "board_id")
	private Long id; // 번호

	@Enumerated(EnumType.STRING)
	private Category category;// 카테고리 [NOTICE, WORK]

	private String title; // 제목
	
	@Column(length = 5000)
	private String content; // 내용	

	@CreatedDate
	private LocalDateTime regDate; // 등록 날짜

	private Long viewCount; // 조회수

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private PmsUser pmsUser; // 작성자
	
	@Column
	private String boardScope;	//공개범위

	public Board update(String title, Category category, String content) {
		this.title = title;
		this.category = category;
		this.content = content;
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