package com.forwiz.pms.web.board.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.forwiz.pms.domain.board.dto.BoardFileResponseDto;
import com.forwiz.pms.domain.board.dto.BoardRequestDto;
import com.forwiz.pms.domain.board.dto.BoardResponseDto;
import com.forwiz.pms.domain.board.entity.Category;
import com.forwiz.pms.domain.board.exception.AccessDenied;
import com.forwiz.pms.domain.board.service.BoardService;
import com.forwiz.pms.domain.file.service.FileService;
import com.forwiz.pms.domain.user.dto.PmsUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;
	private final FileService fileService;

	// 카테고리 리스트 > write/ update 화면에
	@ModelAttribute("categorys")
	public Category[] categorys() {
		return Category.values(); // 해당 ENUM의 모든 정보를 배열로 반환한다.
	}

	/**
	 * 공지 게시판 목록화면
	 * 
	 * @Method : noticeList
	 */
	@GetMapping("/pms/board/notice")
	public String noticeList(String searchVal, Pageable pageable, Model model) {
		Category category = Category.NOTICE;
		Page<BoardResponseDto> results = boardService.selectBoardList(searchVal, pageable, category);
		boardModelPut(results, model, searchVal, pageable, category);
		pageModelPut(results, model);
		return "board/notice";
	}

	/**
	 * 업무 게시판 목록화면
	 * 
	 * @Method : workList
	 */
	@GetMapping("/pms/board/work")
	public String workList(String searchVal, Pageable pageable, Model model) {
		Category category = Category.WORK;
		Page<BoardResponseDto> results = boardService.selectBoardList(searchVal, pageable, category);
		boardModelPut(results, model, searchVal, pageable, category);
		pageModelPut(results, model);
		return "board/work";
	}

	/**
	 * board 처리
	 * 
	 * @Method : boardModelPut
	 */
	private void boardModelPut(Page<BoardResponseDto> results, Model model, String searchVal, Pageable pageable,
			Category category) {
		model.addAttribute("list", results);
		model.addAttribute("searchVal", searchVal);
		model.addAttribute("category", category);
	}

	/**
	 * page처리
	 * 
	 * @Method : pageModelPut
	 */
	private void pageModelPut(Page<BoardResponseDto> results, Model model) {
		model.addAttribute("totalCount", results.getTotalElements());
		model.addAttribute("size", results.getPageable().getPageSize()); // 페이지별 사이즈
		model.addAttribute("number", results.getPageable().getPageNumber()); // 현재페이지 번호
		model.addAttribute("maxPage", 5);
	}

	/**
	 * 게시물 글쓰기 화면
	 * 
	 * @Method : write
	 */
	@GetMapping("/pms/board/write")
	public String write(Model model) {
		// response
		model.addAttribute("boardRequestDto", new BoardResponseDto());
		return "board/write";
	}

	/**
	 * 게시물 등록
	 * 
	 * @Method : save
	 */
	@PostMapping("/pms/board/write")
	public String save(@Valid BoardRequestDto boardRequestDto, BindingResult result) throws Exception {
		// 유효성 검사
		if (result.hasErrors()) { // 오류가 있을경우 다시 글쓰기 화면으로

			return "board/write";
		}
		boardService.saveBoard(boardRequestDto);
		String returnPage = boardService.getReturnPage(boardRequestDto.getCategory());
		return "redirect:/pms/board/"+returnPage;
	}


	/**
	 * 게시물 수정 화면
	 * 
	 * @Method : update (Get)
	 */
	@GetMapping("/pms/board/update/{boardId}")
	public String update(@PathVariable Long boardId, Model model) {
		BoardResponseDto boardResponseDto = boardService.getBoard(boardId);
		List<BoardFileResponseDto> boardFileResponseDto = boardService.getFile(boardId);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PmsUserDetails user =(PmsUserDetails) auth.getPrincipal();	
		if (!(user.getPmsUser().getUserId()).equals((boardResponseDto.getUserId()))){
				throw new AccessDenied("작성자만 수정 가능 합니다.");
		}
		model.addAttribute("boardRequestDto", boardResponseDto);
		model.addAttribute("boardFile", boardFileResponseDto);
		return "board/update";
	}

	/**
	 * 게시물 수정
	 * 
	 * @Method : update
	 */
	@PostMapping("/pms/board/update/{boardId}")
	public String update(@Valid BoardRequestDto boardRequestDto, BindingResult result) throws Exception {
		// 유효성검사
		if (result.hasErrors()) {
			return "board/update";
		}
		boardService.saveBoard(boardRequestDto);
		String returnPage = boardService.getReturnPage(boardRequestDto.getCategory());
		return "redirect:/pms/board/"+returnPage;
	}

	/**
	 * 게시물 상세 조회 화면
	 * 
	 * @Method : detail
	 */
	@GetMapping("/pms/board/detail/{boardId}")
	public String detail(@PathVariable Long boardId, Model model) {
		// response
		boardService.updateViewCount(boardId);
		BoardResponseDto boardResponseDto = boardService.getBoard(boardId);
		List<BoardFileResponseDto> boardFileResponseDto = boardService.getFile(boardId);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PmsUserDetails user =(PmsUserDetails) auth.getPrincipal();	
		if ((user.getPmsUser().getUserId()).equals((boardResponseDto.getUserId()))) {
			model.addAttribute("isWriter",true);
		}
		log.info("현재 로그인 유저 조직 : {}", user.getPmsUser().getOrganization().getOrganizationName());
		log.info("현재 로그인 유저 id : {}", user.getPmsUser().getUserId());
		log.info("게시물 작성자 id : {}", boardResponseDto.getUserId());
		model.addAttribute("boardFile", boardFileResponseDto);
		model.addAttribute("boardDto", boardResponseDto); // th:object="${boardDto}"

		return "board/detail";
	}

	/**
	 * 게시물 삭제
	 * 
	 * @Method : boardDelete
	 */
	@GetMapping("/delete/{boardId}")
	public String boardDelete(@PathVariable Long boardId) {
		BoardResponseDto boardResponseDto = boardService.getBoard(boardId);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PmsUserDetails user =(PmsUserDetails) auth.getPrincipal();	
		if (!(user.getPmsUser().getUserId()).equals((boardResponseDto.getUserId()))){
			throw new AccessDenied("작성자만 삭제 가능 합니다.");
		}
		boardService.deleteBoard(boardId);
		String returnPage = boardService.getReturnPage(boardResponseDto.getCategory());
		return "redirect:/pms/board/" + returnPage;
	}
	

	/**
	 * 파일삭제
	 * 
	 * @Method : boardFileDelete
	 */
	@PostMapping("/boardFileDelete")
	public String boardFileDelete(@RequestParam Long fileId, @RequestParam Long boardId) {
		fileService.deleteBoardFile(fileId); // 파일삭제
		return "redirect:/update/" + boardId;
	}

}