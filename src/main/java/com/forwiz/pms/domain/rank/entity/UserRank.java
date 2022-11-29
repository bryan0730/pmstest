package com.forwiz.pms.domain.rank.entity;

import com.forwiz.pms.domain.organization.entity.Organization;
import com.forwiz.pms.domain.rank.exception.NotSaveRankException;
import com.forwiz.pms.domain.user.entity.PmsUser;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@SequenceGenerator(name="RANK_SEQ_GENERATOR", sequenceName = "RANK_SEQ", allocationSize = 1)
public class UserRank {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RANK_SEQ_GENERATOR")
    private Long rankId;

    private String rankName;

    private Integer rankWeight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @OneToMany(mappedBy = "userRank")
    private List<PmsUser> pmsUsers = new ArrayList<>();

    @Builder
    public UserRank(Long rankId, String rankName, Integer rankWeight, Organization organization){
        verifyRankOrgName(organization);
        this.rankId = rankId;
        this.rankName = rankName;
        this.rankWeight = rankWeight;
        this.organization = organization;
    }

    private void verifyRankOrgName(Organization organization){
        if (organization.getOrganizationName().equals("DEFAULT") && organization.getUserRanks().size()>0){
            throw new NotSaveRankException("DEFAULT 에서 직급은 추가할 수 없습니다.", organization.getOrganizationId());
        }
    }
}
