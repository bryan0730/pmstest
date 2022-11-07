package com.forwiz.pms.domain.message.dto;

import lombok.Getter;

@Getter
public enum MessageState {

    READ("읽음"),
    UNREAD("읽지 않음")
    ;

    private final String status;

    MessageState(String status){
        this.status = status;
    }
}
