package com.forwiz.pms.domain.user.dto;

import com.forwiz.pms.domain.user.entity.PmsUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoResponse {

    private Long id;
    private String userId;
    private String userName;
    private String organizationName;
    private String userPhoneNumber;
    private String userRank;
    private Boolean userDeleteYN;

    public UserInfoResponse(PmsUser user){
        this.id = user.getId();
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.organizationName = user.getUserRank().getOrganization().getOrganizationName();
        this.userPhoneNumber = user.getUserPhoneNumber();
        this.userRank = user.getUserRank().getRankName();
        this.userDeleteYN = user.getUserDeleteYN();
    }
}
