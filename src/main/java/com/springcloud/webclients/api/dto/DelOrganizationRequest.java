package com.springcloud.webclients.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DelOrganizationRequest {

//    private List<Long> orgId;
//    private List<Boolean> isChecked;
    private Long orgId;
    private Boolean isChecked;
}
