package com.forwiz.pms.web.reply.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forwiz.pms.domain.reply.dto.ReplyRequest;
import com.forwiz.pms.domain.reply.service.ReplyService;
import com.forwiz.pms.domain.user.dto.PmsUserDetails;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ReplyApiController {

	private final ReplyService replyService;

	/* create */
	@PostMapping("/board/{id}/reply")
	public ResponseEntity replySave(@PathVariable Long id, @RequestBody ReplyRequest dto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PmsUserDetails user = (PmsUserDetails) auth.getPrincipal();
		return ResponseEntity.ok(replyService.replySave(user.getPmsUser().getId(), id, dto));
	}

	/* UPDATE */
	@PutMapping("/board/{boardid}/reply/{replyid}")
	public ResponseEntity update(@PathVariable Long boardid, @PathVariable Long replyid, @RequestBody ReplyRequest dto) {
		replyService.update(replyid, dto);
		return ResponseEntity.ok(replyid);
	}

	/* DELETE */
	@DeleteMapping("/board/{boardid}/reply/{replyid}")
	public void delete(@PathVariable Long boardid, @PathVariable Long replyid) {
		replyService.delete(replyid);
	}
}
