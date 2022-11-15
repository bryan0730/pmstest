package com.forwiz.pms.domain.board.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.forwiz.pms.domain.board.entity.Category;
import com.querydsl.core.annotations.Immutable;

import lombok.Getter;

@Entity
@Subselect(
    "select " +
    "ROW_NUMBER() over (PARTITION BY b.category ORDER BY b.board_id desc, b.reg_date desc) as Row_Num"
    + ", b.board_id"
    + ", b.title"
    + ", b.category"
    + ", b.reg_date"
    + ", b.view_count"
    + ", b.board_scope"
    + ", p.user_name "
    + "from board b "
    + "left outer join pms_user p on b.id = p.id"
)

@Getter
@Immutable
@Synchronize("Board")
public class BoardSubSelect {

    @Column(name = "Row_Num")
    private Long rowNum;

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "title")
    private String title;
    
	@Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

	@CreatedDate
	@Column(name = "reg_date")
	private LocalDateTime regDate;

	@Column(name = "view_count")
    private Long viewCount;
    
    @Column(name = "user_name")
    private String userName;

    @Column(name = "board_scope")
    private String boardScope;
    
}