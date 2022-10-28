package com.forwiz.pms.domain.organization.dto;

import com.forwiz.pms.domain.organization.entity.Organization;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveOrganizationRequest {

    private String organizationName;
    private String organizationCode;
    private Boolean organizationDelete = false;

    public Organization toEntity(){
        return Organization.builder()
                .organizationCode(organizationCode)
                .organizationName(organizationName)
                .organizationDelete(organizationDelete)
                .build()
                ;
    }
}
