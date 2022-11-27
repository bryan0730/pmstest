package com.forwiz.pms.domain.board.repository;


import static com.forwiz.pms.domain.board.entity.QBoard.board;
import static com.forwiz.pms.domain.board.entity.QBoardFile.boardFile;
import static com.forwiz.pms.domain.user.entity.QPmsUser.pmsUser;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.forwiz.pms.domain.board.dto.BoardFileResponseDto;
import com.forwiz.pms.domain.board.dto.BoardResponseDto;
import com.forwiz.pms.domain.board.dto.QBoardFileResponseDto;
import com.forwiz.pms.domain.board.dto.QBoardResponseDto;
import com.forwiz.pms.domain.board.entity.Category;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class BoardRepositoryImpl implements CustomBoardRepository {

	private final JPAQueryFactory jpaQueryFactory;

	
	public BoardRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
		this.jpaQueryFactory = jpaQueryFactory;
	}

	//게시판 목록
	@Override
	public Page<BoardResponseDto> selectBoardList(String searchVal, Pageable pageable, Category category,
			String organizationName) {

		List<BoardResponseDto> boardList = getBoardDtos(searchVal, pageable, category, organizationName);

		int start = (int) pageable.getOffset();
		int end = (start + pageable.getPageSize()) > boardList.size() 
				? boardList.size()
				: (start + pageable.getPageSize());
		return new PageImpl<>(boardList.subList(start, end), pageable, boardList.size());
	}

	//%키워드% 조회
	private BooleanExpression containsSearch(String searchVal) {
		return searchVal != null ? board.title.contains(searchVal) : null; 
	}
	
	//%키워드% 조회

	private List<BoardResponseDto> getBoardDtos(String searchVal, Pageable pageable, Category category, String organizationName){
        List<BoardResponseDto> content = jpaQueryFactory
                .select(new QBoardResponseDto(
                         board.id
                        ,board.title
                        ,board.category
                        ,board.content
                        ,board.regDate
                        ,board.viewCount
                        ,pmsUser.userName
                        ,pmsUser.userOrganizationName))
                .from(board)
                .leftJoin(board.pmsUser, pmsUser)
                .where(containsSearch(searchVal))
                .where(board.category.eq(category))
                .where(board.boardScope.eq(organizationName).or(board.boardScope.eq("전체")))
                .orderBy(board.id.desc())
                .fetch();
        return content;
    }
 
	//게시물 첨부파일 리스트
	 @Override
	    public List<BoardFileResponseDto> selectBoardFileDetail(Long boardId) {
	        List<BoardFileResponseDto> content = jpaQueryFactory
	                .select(new QBoardFileResponseDto(
	                         boardFile.id
	                        ,boardFile.fileInfo.id
	                        ,boardFile.fileInfo.originFileName
	                        ,boardFile.fileInfo.fileVolume
	                        ,boardFile.fileInfo.extension
	                        ,boardFile.delYn
	                ))
	                .from(boardFile)
	                .leftJoin(boardFile.fileInfo)
	                .where(boardFile.boardId.eq(boardId))
	                .where(boardFile.delYn.eq("N"))
	                .fetch();
	        return content;
	    }
}

