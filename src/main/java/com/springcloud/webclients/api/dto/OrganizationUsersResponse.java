package com.springcloud.webclients.api.dto;

import com.springcloud.webclients.api.entity.Organization;
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

    private List<UserInfoResponse> userList;

    /*
    조직이 추가되거나 삭제되거나 수정될때
    Cache와 Interceptor로 인해 /admin/organization에서 pmsUser 테이블을
    select하는 구문이 여러번(N+1) 발생하는 이유가 이 클래스 생성자에서
    userList 때문인듯?
     */
    public OrganizationUsersResponse(Organization organization){
        this.organizationId = organization.getOrganizationId();
        this.organizationName = organization.getOrganizationName();
        this.organizationCode = organization.getOrganizationCode();
        this.organizationDelete = organization.getOrganizationDelete();
        this.userList = organization.getPmsUsers().stream()
                .map(UserInfoResponse::new)
                .collect(Collectors.toList());
    }
}
