package com.forwiz.pms.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forwiz.pms.domain.board.entity.BoardFile;

public interface BoardFileRepository extends JpaRepository<BoardFile, Long>{

}
