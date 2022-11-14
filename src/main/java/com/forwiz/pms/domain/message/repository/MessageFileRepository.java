package com.forwiz.pms.domain.message.repository;

import com.forwiz.pms.domain.message.entity.MessageFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageFileRepository extends JpaRepository<MessageFile, Long> {

}
