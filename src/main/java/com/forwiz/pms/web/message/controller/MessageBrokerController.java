package com.forwiz.pms.web.message.controller;

import com.forwiz.pms.domain.message.dto.MessageRequest;
import com.forwiz.pms.domain.message.dto.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class MessageBrokerController {

    @MessageMapping("/pms/{receiver}")
    @SendTo("/topic/pms-message/{receiver}")
    public MessageResponse getMessage(@DestinationVariable Long receiver, MessageRequest messageRequest){
        return new MessageResponse(messageRequest.getMessageId(), messageRequest.getSender(),messageRequest.getMessageContent(), messageRequest.getSenderName());
    }


}
