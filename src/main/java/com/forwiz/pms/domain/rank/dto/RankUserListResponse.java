package com.forwiz.pms.domain.rank.dto;

import com.forwiz.pms.domain.rank.entity.UserRank;
import com.forwiz.pms.domain.user.dto.UserInfoResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class RankUserListResponse {

    private Long rankId;
    private String rankName;
    private Integer rankWeight;
    private List<UserInfoResponse> userList;

    public RankUserListResponse(UserRank userRank){
        this.rankId = userRank.getRankId();
        this.rankName = userRank.getRankName();
        this.rankWeight = userRank.getRankWeight();
        this.userList = userRank.getPmsUsers().stream()
                .map(UserInfoResponse::new)
                .collect(Collectors.toList());
    }
}
