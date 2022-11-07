package com.forwiz.pms.domain.message.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageSaveRequest {

    private Long messageReceiver;
    private Long messageSender;
    private String comment;
    private Date sendDate;

}
