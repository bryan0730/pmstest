package com.springcloud.webclients.api.dto;

import com.springcloud.webclients.api.entity.MyUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoResponse {

    private Long id;
    private String userId;
    private String userName;
    private String userPhoneNumber;

    public UserInfoResponse(MyUser myUser){
        this.id = myUser.getId();
        this.userId = myUser.getUserId();
        this.userName = myUser.getUserName();
        this.userPhoneNumber = myUser.getUserPhoneNumber();
    }
}
