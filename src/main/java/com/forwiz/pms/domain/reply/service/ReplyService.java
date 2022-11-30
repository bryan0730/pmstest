package com.forwiz.pms.domain.reply.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forwiz.pms.domain.board.entity.Board;
import com.forwiz.pms.domain.board.repository.BoardRepository;
import com.forwiz.pms.domain.reply.dto.ReplyRequest;
import com.forwiz.pms.domain.reply.entity.Reply;
import com.forwiz.pms.domain.reply.repository.ReplyRepository;
import com.forwiz.pms.domain.user.entity.PmsUser;
import com.forwiz.pms.domain.user.repository.PmsUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyService {
	private final ReplyRepository replyrepository;
	private final PmsUserRepository pmsUserRepository;
	private final BoardRepository boardRepository;

	/* create */
	public Long replySave(Long userId, Long id, ReplyRequest replyRequest) {
		PmsUser user = pmsUserRepository.findById(userId).get();
		Board board = boardRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다." + id));

		replyRequest.setReplier(user);
		replyRequest.setBoard(board);

		Reply reply = replyRequest.toEntity();
		replyrepository.save(reply);

		return replyRequest.getId();
	}

	/* UPDATE */
	@Transactional
	public void update(Long id, ReplyRequest dto) {
		Reply reply = replyrepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다. " + id));

		reply.update(dto.getContent());
	}

	/* DELETE */
	@Transactional
	public void delete(Long id) {
		Reply reply = replyrepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id=" + id));

		replyrepository.delete(reply);
	}
}
