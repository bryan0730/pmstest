package com.forwiz.pms.domain.rank.dto;

import com.forwiz.pms.domain.rank.entity.UserRank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RankInfoResponse {

    private Long rankId;
    private String rankName;
    private Integer rankWeight;

    public RankInfoResponse(UserRank userRank){
        this.rankId = userRank.getRankId();
        this.rankName = userRank.getRankName();
        this.rankWeight = userRank.getRankWeight();
    }

}
