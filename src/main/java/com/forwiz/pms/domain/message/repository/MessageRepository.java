package com.forwiz.pms.domain.message.repository;

import com.forwiz.pms.domain.message.dto.MessageState;
import com.forwiz.pms.domain.message.entity.Message;
import com.forwiz.pms.domain.user.entity.PmsUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByReceiver(PmsUser receiver);

    int countByMessageStateAndReceiver(MessageState messageState, PmsUser receiverId);
}
