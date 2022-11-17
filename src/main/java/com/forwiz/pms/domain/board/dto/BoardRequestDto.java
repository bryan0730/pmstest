package com.forwiz.pms.domain.board.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import com.forwiz.pms.domain.annotation.ValidEnum;
import com.forwiz.pms.domain.board.entity.Board;
import com.forwiz.pms.domain.board.entity.Category;
import com.forwiz.pms.domain.user.entity.PmsUser;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

/*Board에 추가할 데이터를 입력할 때 사용*/
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardRequestDto {

	private Long id; // 시퀀스
	@NotEmpty(message = "제목은 필수입니다.")
	private String title; // 제목

	@ValidEnum(enumClass = Category.class, message = "카테고리는 필수입니다.")
	private Category category; // 카테고리

	private String content; // 내용

	private String boardScope;

	private List<MultipartFile> multipartFile;
	
	public Board toEntity(PmsUser pmsUser) {
		return Board
				.builder()
				.pmsUser(pmsUser)
				.title(title)
				.category(category)
				.content(content)
				.boardScope(boardScope)
				.build();
	}
}
