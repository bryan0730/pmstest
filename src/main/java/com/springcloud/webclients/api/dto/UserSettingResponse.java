package com.springcloud.webclients.api.dto;

import com.springcloud.webclients.api.entity.MyUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSettingResponse {

    private Long id;
    private String userId;
    private String userName;
    private AllOrganizationResponse organization;
    private String userPhoneNumber;
    private String userRank;
    private Boolean userDeleteYN;

    public UserSettingResponse(MyUser user){
        this.id = user.getId();
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.organization = new AllOrganizationResponse(user.getOrganization());
        this.userPhoneNumber = user.getUserPhoneNumber();
        this.userRank = user.getUserRank();
        this.userDeleteYN = user.getUserDeleteYN();
    }
}
