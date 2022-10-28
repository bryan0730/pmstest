package com.forwiz.pms.domain.organization.dto;

import com.forwiz.pms.domain.organization.entity.Organization;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationListResponse {

    private Long organizationId;
    private String organizationName;
    private String organizationCode;
    private Boolean organizationDelete;

    public OrganizationListResponse(Organization organization){
        this.organizationId = organization.getOrganizationId();
        this.organizationName = organization.getOrganizationName();
        this.organizationCode = organization.getOrganizationCode();
        this.organizationDelete = organization.getOrganizationDelete();
    }
}
