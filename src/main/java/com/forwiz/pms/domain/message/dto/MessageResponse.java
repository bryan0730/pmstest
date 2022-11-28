package com.forwiz.pms.domain.message.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {

    private Long messageId;
    private String messageSender;
    private String messageContent;
    private String messageSenderName;
}
