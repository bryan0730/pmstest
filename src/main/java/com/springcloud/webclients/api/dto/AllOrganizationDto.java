package com.springcloud.webclients.api.dto;

import com.springcloud.webclients.api.entity.Organization;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllOrganizationDto {

    private Long organizationId;
    private String organizationName;

    public AllOrganizationDto(Organization organization){
        this.organizationId = organization.getOrganizationId();
        this.organizationName = organization.getOrganizationName();
    }
}
