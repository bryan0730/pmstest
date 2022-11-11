package com.forwiz.pms.domain.message.dto;

import com.forwiz.pms.domain.message.entity.Message;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MessageSendListResponse {

    private Long messageId;
    private Long receiverId;
    private String messageContents;
    private String receiverName;
    private String messageState;
    private Date receiveDate;

    public MessageSendListResponse(Message message){
        this.messageId = message.getMessageId();
        this.receiverId = message.getReceiver().getId();
        this.messageContents = message.getComments();
        this.receiverName = message.getReceiver().getUserName();
        this.messageState = message.getMessageState().getStatus();
        this.receiveDate = message.getSendDate();
    }
}
