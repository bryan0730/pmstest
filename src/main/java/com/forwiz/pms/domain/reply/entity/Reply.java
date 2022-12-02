package com.forwiz.pms.domain.reply.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.forwiz.pms.domain.board.entity.Board;
import com.forwiz.pms.domain.user.entity.PmsUser;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "REPLY_SEQ_GENERATOR", sequenceName = "REPLY_SEQ", allocationSize = 1)
public class Reply {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REPLY_SEQ_GENERATOR")
	@Column(name = "reply_id")
	private Long id;

	@Column(nullable = false)
	private String content;

	@CreatedDate
	private LocalDateTime regDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id", nullable = false)
	private Board board;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private PmsUser replier;

	private Boolean delYN;

	public void updateDelYN(boolean delYN) {
		this.delYN = delYN;
	}
    /* 수정 */
    public void update(String content) {
        this.content = content;
    }
}
