package com.forwiz.pms.domain.message.service;

import com.forwiz.pms.domain.message.dto.MessageReceiveListResponse;
import com.forwiz.pms.domain.message.dto.NoticePopupResponse;
import com.forwiz.pms.domain.message.dto.MessageSaveRequest;
import com.forwiz.pms.domain.message.dto.MessageState;
import com.forwiz.pms.domain.message.entity.Message;
import com.forwiz.pms.domain.message.repository.MessageRepository;
import com.forwiz.pms.domain.user.entity.PmsUser;
import com.forwiz.pms.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;

    @Transactional
    public void saveMessage(MessageSaveRequest messageSaveRequest) {

        PmsUser sender = userService.findById(messageSaveRequest.getMessageSender());
        PmsUser receiver = userService.findById(messageSaveRequest.getMessageReceiver());

        Message message = Message.builder()
                .comments(messageSaveRequest.getComment())
                .sendDate(messageSaveRequest.getSendDate())
                .sender(sender)
                .receiver(receiver)
                .messageState(MessageState.UNREAD)
                .build();
        messageRepository.save(message);
    }

    @Transactional(readOnly = true)
    public List<NoticePopupResponse> findNoticePopupInfo(PmsUser receiver, Date referenceTime){

        return messageRepository.findByReceiverAndSendDateOrderByMessageId(receiver, referenceTime)
                .stream()
                .map(NoticePopupResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public int countByMessageState(MessageState messageState, PmsUser receiverId){
        return messageRepository.countByMessageStateAndReceiver(messageState, receiverId);
    }

    @Transactional(readOnly = true)
    public List<MessageReceiveListResponse> findByReceiver(Long id){



        return null;
    }
}
