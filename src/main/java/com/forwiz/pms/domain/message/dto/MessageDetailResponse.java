package com.forwiz.pms.domain.message.dto;

import com.forwiz.pms.domain.message.entity.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class MessageDetailResponse {

    private Long messageId;
    private Long receiverId;
    private Long senderId;
    private String comments;
    private Date sendDate;
    private String senderName;
    private String receiverName;


    public MessageDetailResponse(Message message){
        this.messageId = message.getMessageId();
        this.receiverId = message.getReceiver().getId();
        this.senderId = message.getSender().getId();
        this.comments = message.getComments();
        this.sendDate = message.getSendDate();
        this.senderName = message.getSender().getUserName();
        this.receiverName = message.getReceiver().getUserName();
    }
}
