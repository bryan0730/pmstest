package com.forwiz.pms.domain.user.dto;

import com.forwiz.pms.domain.rank.dto.RankFormResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserSettingFormResponse {

    private List<UserInfoResponse> userInfoList;
    private RankFormResponse orgAndRankData;
}
