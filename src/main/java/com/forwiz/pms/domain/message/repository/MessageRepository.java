package com.forwiz.pms.domain.message.repository;

import com.forwiz.pms.domain.message.dto.MessageState;
import com.forwiz.pms.domain.message.entity.Message;
import com.forwiz.pms.domain.user.entity.PmsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("select m from Message m where m.receiver=?1 and m.sendDate>?2 " +
            "order by m.messageId desc ")
    List<Message> findByReceiverAndSendDateOrderByMessageId(PmsUser receiver, Date referenceTime);

    int countByMessageStateAndReceiver(MessageState messageState, PmsUser receiverId);

    List<Message> findByReceiver(PmsUser receiver);
}
