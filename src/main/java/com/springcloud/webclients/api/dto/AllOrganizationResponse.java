package com.springcloud.webclients.api.dto;

import com.springcloud.webclients.api.entity.Organization;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class AllOrganizationResponse {

    private Long organizationId;
    private String organizationName;
    private String organizationCode;
    private Boolean organizationDelete;

    private List<UserInfoResponse> userList;

    public AllOrganizationResponse(Organization organization){
        this.organizationId = organization.getOrganizationId();
        this.organizationName = organization.getOrganizationName();
        this.organizationCode = organization.getOrganizationCode();
        this.organizationDelete = organization.getOrganizationDelete();
        this.userList = organization.getPmsUsers().stream()
                .map(UserInfoResponse::new)
                .collect(Collectors.toList());
    }
}
