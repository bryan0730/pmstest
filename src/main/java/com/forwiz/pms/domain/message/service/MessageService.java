package com.forwiz.pms.domain.message.service;

import com.forwiz.pms.domain.message.dto.*;
import com.forwiz.pms.domain.message.entity.Message;
import com.forwiz.pms.domain.message.repository.MessageRepository;
import com.forwiz.pms.domain.user.dto.PmsUserDetails;
import com.forwiz.pms.domain.user.entity.PmsUser;
import com.forwiz.pms.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
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
    public List<MessageReceiveListResponse> findByReceiver(Authentication authentication){

        PmsUser receiver = getUserInfo(authentication);

        return messageRepository.findByReceiver(receiver)
                .stream()
                .map(MessageReceiveListResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MessageSendListResponse> findBySender(Authentication authentication) {

        PmsUser sender = getUserInfo(authentication);

        return messageRepository.findBySender(sender)
                .stream()
                .map(MessageSendListResponse::new)
                .collect(Collectors.toList());
    }

    private PmsUser getUserInfo(Authentication authentication) {
        PmsUserDetails principal = (PmsUserDetails) authentication.getPrincipal();
        PmsUser user = principal.getPmsUser();
        return user;
    }
}
