package com.forwiz.pms.domain.reply.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forwiz.pms.domain.reply.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

}
