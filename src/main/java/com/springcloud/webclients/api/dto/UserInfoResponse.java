package com.springcloud.webclients.api.dto;

import com.springcloud.webclients.api.entity.PmsUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoResponse {

    private Long id;
    private String userId;
    private String userName;
    private String userPhoneNumber;

    public UserInfoResponse(PmsUser pmsUser){
        this.id = pmsUser.getId();
        this.userId = pmsUser.getUserId();
        this.userName = pmsUser.getUserName();
        this.userPhoneNumber = pmsUser.getUserPhoneNumber();
    }
}
