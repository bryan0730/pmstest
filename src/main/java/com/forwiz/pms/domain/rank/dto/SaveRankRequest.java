package com.forwiz.pms.domain.rank.dto;

import com.forwiz.pms.domain.organization.entity.Organization;
import com.forwiz.pms.domain.rank.entity.UserRank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveRankRequest {

    private Long organizationId;
    private String rankName;
    private Integer rankWeight;

    public SaveRankRequest(Long organizationId, String rankName, Integer rankWeight){
        this.organizationId = organizationId;
        this.rankName = rankName;
        this.rankWeight = rankWeight;
    }

    public UserRank toEntity(final Organization organization){
        return UserRank.builder()
                .rankName(rankName)
                .rankWeight(rankWeight)
                .organization(organization)
                .build();
    }
}
