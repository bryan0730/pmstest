package com.forwiz.pms.web.board.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.forwiz.pms.domain.board.service.BoardService;
import com.forwiz.pms.domain.file.service.FileService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {

//	private final CustomBoardRepository customBoardRepository;
	private final BoardService boardService;
	private final FileService fileService;

	// 카테고리 리스트 > write/ update 화면에
	@ModelAttribute("categorys")
	public Category[] categorys() {
		return Category.values(); // 해당 ENUM의 모든 정보를 배열로 반환한다.
	}

	/**
	 * 메인화면
	 * 
	 * @Method : main
	 */
//	@GetMapping("/board")
//	public String main() {
//		return "views/content";
//	}

	/**
	 * 공지 게시판 목록화면
	 * 
	 * @Method : noticeList
	 */
	@GetMapping("/notice")
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
	@GetMapping("/work")
	public String workList(String searchVal, Pageable pageable, Model model) {
		Category category = Category.WORK;
		Page<BoardResponseDto> results = boardService.selectBoardList(searchVal, pageable, category);
		boardModelPut(results, model, searchVal, pageable, category);
		pageModelPut(results, model);
		return "board/work";
	}

	/**
	 * workList > board 처리
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
	 * workList > page처리
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
	@GetMapping("/write")
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
	@PostMapping("/write")
	public String save(@Valid BoardRequestDto boardRequestDto, BindingResult result) throws Exception {
		// 유효성 검사
		if (result.hasErrors()) { // 오류가 있을경우 다시 글쓰기 화면으로

			return "board/write";
		}
		boardService.saveBoard(boardRequestDto);

		if (boardRequestDto.getCategory() == Category.NOTICE) {
			return "redirect:/notice";
		}
		return "redirect:/work";
	}

	/**
	 * 게시물 수정 화면
	 * 
	 * @Method : update (Get)
	 */
	@GetMapping("/update/{boardId}")
	public String update(@PathVariable Long boardId, Model model) {
		BoardResponseDto boardResponseDto = boardService.getBoard(boardId);
		List<BoardFileResponseDto> boardFileResponseDto = boardService.getFile(boardId);
		model.addAttribute("boardRequestDto", boardResponseDto);
		model.addAttribute("boardFile", boardFileResponseDto);
		return "board/update";
	}

	/**
	 * (put 수정) 게시물 수정
	 * 
	 * @Method : update
	 */
	@PostMapping("/update/{boardId}")
	public String update(@Valid BoardRequestDto boardRequestDto, BindingResult result) throws Exception {
		// 유효성검사
		if (result.hasErrors()) {
			return "board/update";
		}
		boardService.saveBoard(boardRequestDto);
		if (boardRequestDto.getCategory() == Category.NOTICE) {
			return "redirect:/notice";
		}
		return "redirect:/work";
	}

	/**
	 * 게시물 상세 조회 화면
	 * 
	 * @Method : detail
	 */
	@GetMapping("/detail/{boardId}")
	public String detail(@PathVariable Long boardId, Model model) {
		// response
		boardService.updateViewCount(boardId);
		BoardResponseDto boardResponseDto = boardService.getBoard(boardId);
		List<BoardFileResponseDto> boardFileResponseDto = boardService.getFile(boardId);
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
		boardService.deleteBoard(boardId);
		if (boardResponseDto.getCategory() == Category.NOTICE) {
			return "redirect:/notice";
		}
		return "redirect:/work";
		// TODO: 화면 이동 어떻게 바꿀수??
		// 화면이동 코드 중복되는게 많다
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