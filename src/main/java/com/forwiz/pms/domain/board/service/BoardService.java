package com.forwiz.pms.domain.board.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forwiz.pms.domain.board.dto.BoardFileResponseDto;
import com.forwiz.pms.domain.board.dto.BoardRequestDto;
import com.forwiz.pms.domain.board.dto.BoardResponseDto;
import com.forwiz.pms.domain.board.entity.Board;
import com.forwiz.pms.domain.board.entity.Category;
import com.forwiz.pms.domain.board.repository.BoardRepository;
import com.forwiz.pms.domain.board.repository.CustomBoardRepository;
import com.forwiz.pms.domain.file.service.FileService;
import com.forwiz.pms.domain.user.dto.PmsUserDetails;
import com.forwiz.pms.domain.user.entity.PmsUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;
	//private final MemberRepository memberRepository; >> PmsUserRepository pmsUserRepository;
	private final CustomBoardRepository customBoardRepository;
	private final FileService fileService;
	/**
	 * 게시물 등록
	 * @Method : saveBoard
	 */
	@Transactional
	public Long saveBoard(BoardRequestDto boardRequestDto) throws Exception {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PmsUserDetails user = (PmsUserDetails) auth.getPrincipal();
		PmsUser member = user.getPmsUser();

		Board board = null;

		//insert
		if (boardRequestDto.getId() == null) {
			board = boardRequestDto.toEntity(member);
			boardRepository.save(board);
		}
		
		//update
		else {
            board = boardRepository.findById(boardRequestDto.getId()).get();
			board.update(boardRequestDto.getTitle(),boardRequestDto.getCategory(), boardRequestDto.getContent());
		}
		
		//파일 저장
		fileService.saveFile(boardRequestDto, board.getId());
		
		
		return board.getId();
	}

	/**
	 * 게시물 상세 조회
	 * @Method : getBoard
	 */
	@Transactional
	public BoardResponseDto getBoard(Long boardId) {
		//Optional<Board> getBoard = boardRepository.findById(boardId);
		//Board board = getBoard.get();
		Board board =  boardRepository.findById(boardId).get();
		
	    //reponse
		BoardResponseDto boardResponseDto = BoardResponseDto.builder()
		.id(boardId)
		.title(board.getTitle())
		.userName(board.getPmsUser().getUserName())
		.category(board.getCategory())
		.content(board.getContent())
		.regDate(board.getRegDate())
		.userId(board.getPmsUser().getUserId())
		.boardScope(board.getBoardScope())
		.build();
		
		return boardResponseDto;
	}

	/**
	 * 게시물 첨부 파일 불러오기
	 * @Method : getFile
	 */
	@Transactional
	public List<BoardFileResponseDto> getFile(Long boardId) {
		return customBoardRepository.selectBoardFileDetail(boardId);
	}

	/**
	 * 게시물 삭제
	 * @Method : deleteBoard
	 */
	@Transactional
	public void deleteBoard(Long boardId) {
		Board board = boardRepository.findById(boardId).get();
		this.boardRepository.delete(board);
	}

	/**
	 * 게시판 목록 불러오기
	 * @Method : selectBoardList
	 */
	@Transactional
	public Page<BoardResponseDto> selectBoardList(String searchVal, Pageable pageable, Category category) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PmsUserDetails user = (PmsUserDetails) auth.getPrincipal();
		String organizationName = user.getPmsUser().getOrganization().getOrganizationName();
		return customBoardRepository.selectBoardList(searchVal, pageable, category, organizationName);
	}

	/**
	 * 게시물 조회수 증가
	 * @Method : updateViewCount
	 */
	@Transactional
	public void updateViewCount(Long boardId) {
		Optional<Board> getBoard = boardRepository.findById(boardId);
		Board board = getBoard.get();
		board.updateViewCount(board.getViewCount());
		boardRepository.save(board); // 조회수 저장
	}

	/**
	 * 게시물 등록, 삭제, 수정시 카테고리별 리턴 할 페이지
	 * @Method : getReturnPage
	 */
	public String getReturnPage(Category category) {
		return category.toString().toLowerCase();
	}

}
