package com.forwiz.pms.domain.message.dto;

import com.forwiz.pms.domain.message.entity.Message;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MessageReceiveListResponse {

    private Long messageId;
    private Long senderId;
    private String messageContents;
    private String senderName;
    private String messageState;
    private Date sendDate;

    public MessageReceiveListResponse(Message message){
        this.messageId = message.getMessageId();
        this.senderId = message.getSender().getId();
        this.messageContents = message.getComments();
        this.senderName = message.getSender().getUserName();
        this.messageState = message.getMessageState().getStatus();
        this.sendDate = message.getSendDate();
    }
}
