package com.forwiz.pms.domain.message.service;

import com.forwiz.pms.domain.message.dto.MessageSaveRequest;
import com.forwiz.pms.domain.message.entity.Message;
import com.forwiz.pms.domain.message.repository.MessageRepository;
import com.forwiz.pms.domain.user.entity.PmsUser;
import com.forwiz.pms.domain.user.repository.PmsUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final PmsUserRepository userRepository;

    @Transactional
    public void saveMessage(MessageSaveRequest messageSaveRequest) {

        PmsUser sender = userRepository.findById(messageSaveRequest.getMessageSender())
                .orElseThrow(() -> new IllegalArgumentException("no search member"));
        PmsUser receiver = userRepository.findById(messageSaveRequest.getMessageReceiver())
                .orElseThrow(() -> new IllegalArgumentException("no search member"));

        Message message = Message.builder()
                .comments(messageSaveRequest.getComment())
                .sendDate(messageSaveRequest.getSendDate())
                .sender(sender)
                .receiver(receiver)
                .build();
        messageRepository.save(message);
    }
}
