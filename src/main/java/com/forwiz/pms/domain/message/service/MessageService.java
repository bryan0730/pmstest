package com.forwiz.pms.domain.message.service;

import com.forwiz.pms.domain.message.dto.*;
import com.forwiz.pms.domain.message.entity.Message;
import com.forwiz.pms.domain.message.exception.NoSearchMessageException;
import com.forwiz.pms.domain.message.repository.MessageRepository;
import com.forwiz.pms.domain.user.dto.PmsUserDetails;
import com.forwiz.pms.domain.user.entity.PmsUser;
import com.forwiz.pms.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;
    private final MessageFileService messageFileService;

    @Transactional
    public Long saveMessage(MessageSaveRequest messageSaveRequest) throws IOException {

        PmsUser sender = userService.findById(messageSaveRequest.getMessageSender());
        PmsUser receiver = userService.findById(messageSaveRequest.getMessageReceiver());

        Message message = Message.builder()
                .comments(messageSaveRequest.getComment())
                .sendDate(messageSaveRequest.getSendDate())
                .sender(sender)
                .receiver(receiver)
                .messageState(MessageState.UNREAD)
                .build();
        Message savedMessage = messageRepository.save(message);

        if (messageSaveRequest.getMessageFiles()!=null){
            messageFileService.saveMessageFile(savedMessage, messageSaveRequest.getMessageFiles());
        }

        return savedMessage.getMessageId();
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
    public List<MessageReceiveListResponse> findByReceiver(){

        PmsUser receiver = getUserInfo();

        return messageRepository.findByReceiverOrderByMessageIdDesc(receiver)
                .stream()
                .map(MessageReceiveListResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MessageSendListResponse> findBySender() {

        PmsUser sender = getUserInfo();

        return messageRepository.findBySenderOrderByMessageIdDesc(sender)
                .stream()
                .map(MessageSendListResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public MessageDetailResponse findByMessageId(Long messageId) {

        Message messageInfo = messageRepository.findById(messageId)
                .orElseThrow(NoSearchMessageException::new);

        Long senderId = messageInfo.getSender().getId();
        Long receiverId = messageInfo.getReceiver().getId();

        //검증 로직(해당 메시지 상세조회 접근 권한이 있는가 확인)
        //해당 ID 메시지의 receiver id(sender id)가 context에 있는 id와 같은가를 확인
        if(!isAuth(id -> id.equals(senderId), id -> id.equals(receiverId))){
            throw new NoSearchMessageException();
        }

        if (isSame(receiverId).apply(getUserInfo().getId())){
            messageInfo.updateMessageState(MessageState.READ);
        }

        return new MessageDetailResponse(messageInfo);
    }

    private PmsUser getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PmsUserDetails principal = (PmsUserDetails) authentication.getPrincipal();
        return principal.getPmsUser();
    }
    private boolean isAuth(Function<Long, Boolean> f1, Function<Long, Boolean> f2){
        PmsUserDetails user = (PmsUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long loginUserId = user.getPmsUser().getId();

        return f1.apply(loginUserId) || f2.apply(loginUserId);
    }
    private Function<Long, Boolean> isSame(Long id){
        return (sessionId) -> sessionId.equals(id);
    }

}
