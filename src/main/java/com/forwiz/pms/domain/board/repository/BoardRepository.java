package com.forwiz.pms.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forwiz.pms.domain.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{
	
}
