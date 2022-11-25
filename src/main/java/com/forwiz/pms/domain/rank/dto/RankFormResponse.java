package com.forwiz.pms.domain.rank.dto;

import com.forwiz.pms.domain.organization.dto.OrganizationListResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RankFormResponse {

    List<RankInfoResponse> rankList;
    List<OrganizationListResponse> organizationList;
}
