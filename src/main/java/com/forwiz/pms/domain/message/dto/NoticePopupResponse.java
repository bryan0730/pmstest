package com.forwiz.pms.domain.message.dto;

import com.forwiz.pms.domain.message.entity.Message;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticePopupResponse {

    private String messageContent;
    private String messageSender;
    private String messageState;

    public NoticePopupResponse(Message message){
        this.messageContent = message.getComments();
        this.messageSender = message.getSender().getUserName();
        this.messageState = message.getMessageState().getStatus();
    }
}
