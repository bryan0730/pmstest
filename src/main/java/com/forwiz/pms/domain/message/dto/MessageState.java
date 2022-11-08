package com.forwiz.pms.domain.message.dto;

import lombok.Getter;

@Getter
public enum MessageState {

    READ("0"),
    UNREAD("1")
    ;

    private final String status;

    MessageState(String status){
        this.status = status;
    }
}
