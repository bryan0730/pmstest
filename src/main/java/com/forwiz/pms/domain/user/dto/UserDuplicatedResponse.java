package com.forwiz.pms.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDuplicatedResponse {

    private Boolean isDuplicated;
    private String responseMessage;
}
