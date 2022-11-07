package com.forwiz.pms.domain.message.repository;

import com.forwiz.pms.domain.message.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
