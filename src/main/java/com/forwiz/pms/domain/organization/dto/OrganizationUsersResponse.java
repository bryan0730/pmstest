package com.forwiz.pms.domain.organization.dto;

import com.forwiz.pms.domain.organization.entity.Organization;
import com.forwiz.pms.domain.rank.dto.RankUserListResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class OrganizationUsersResponse {

    private Long organizationId;
    private String organizationName;
    private String organizationCode;
    private Boolean organizationDelete;
    private List<RankUserListResponse> rankList;

    public OrganizationUsersResponse(Organization organization){
        this.organizationId = organization.getOrganizationId();
        this.organizationName = organization.getOrganizationName();
        this.organizationCode = organization.getOrganizationCode();
        this.organizationDelete = organization.getOrganizationDelete();
        this.rankList = organization.getUserRanks().stream()
                .map(RankUserListResponse::new)
                .collect(Collectors.toList());
    }
}
