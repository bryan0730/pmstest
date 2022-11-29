package com.forwiz.pms.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoChangeForm {

    private String userPhoneNumber;
    private String userPw;

    public UserInfoChangeForm(String userPhoneNumber, String userPw){
        this.userPhoneNumber = userPhoneNumber;
        this.userPw = userPw;
    }
}
