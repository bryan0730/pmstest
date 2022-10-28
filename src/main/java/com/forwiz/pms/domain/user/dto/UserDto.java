package com.forwiz.pms.domain.user.dto;

import com.forwiz.pms.domain.user.entity.PmsUser;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;

    private String userId;

    private String userPw;

    private String userGroup;

    private String userName;
    private Role auth;

    private String userPhoneNumber;

    private String userRank;


//  Dto -> Entity
    public PmsUser toEntity(){

        return PmsUser.builder()
                .userId(userId)
                .userPw(userPw)

                //.userGroup(userGroup)
                .userName(userName)
                .auth(auth)
                .build();
    }

//  Entity -> Dto
    /*
    User 엔티티 클래스에 직접 세션을 저장하려면 직렬화를 해야 하는데,
    엔티티 클래스에 직렬화를 해준다면 추후에 다른 엔티티와 연관관계를 맺을 시 직렬화 대상에 다른 엔티티까지 포함될 수 있어
    성능 이슈, 부수 효과 우려가 있다. -스프링 부트와 AWS로 혼자 구현하는 웹 서비스 中-
     */
    public UserDto(PmsUser pmsUser){
        this.id = pmsUser.getId();
        this.userId = pmsUser.getUserId();
        this.userPw = pmsUser.getUserPw();
        this.userGroup = pmsUser.getOrganization().getOrganizationName();
        this.userName = pmsUser.getUserName();
        this.auth = pmsUser.getAuth();
        this.userPhoneNumber = pmsUser.getUserPhoneNumber();
        this.userRank = pmsUser.getUserRank();
    }

}
