package com.forwiz.pms.domain.message.dto;

import lombok.*;

import java.util.Date;

@Getter
@NoArgsConstructor
public class MessageRequest {

    private Long messageId;
    private String messageContent;
    private String sender;
    private String senderName;

    @Builder
    public MessageRequest(Long messageId, String messageContent, String sender, String senderName){
        this.messageId = messageId;
        this.messageContent = messageContent;
        this.sender = sender;
        this.senderName = senderName;
    }


}
