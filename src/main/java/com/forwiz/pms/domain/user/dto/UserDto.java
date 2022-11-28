package com.forwiz.pms.domain.user.dto;

import com.forwiz.pms.domain.user.entity.PmsUser;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    @NotBlank
    private String userId;

    @NotBlank
    private String userPw;

    @NotNull
    private Long userGroup;

    @NotBlank
    private String userName;

    @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = "핸드폰 번호의 양식과 맞지 않습니다. 01x-xxxx-xxxx")
    private String userPhoneNumber;

    @NotNull
    private Long rankId;

    public PmsUser toEntity(){

        return PmsUser.builder()
                .userId(userId)
                .userPw(userPw)

                //.userGroup(userGroup)
                .userName(userName)
                .auth(Role.ROLE_USER)
                .build();
    }

    /*
    User 엔티티 클래스에 직접 세션을 저장하려면 직렬화를 해야 하는데,
    엔티티 클래스에 직렬화를 해준다면 추후에 다른 엔티티와 연관관계를 맺을 시 직렬화 대상에 다른 엔티티까지 포함될 수 있어
    성능 이슈, 부수 효과 우려가 있다. -스프링 부트와 AWS로 혼자 구현하는 웹 서비스 中-
     */
    public UserDto(PmsUser pmsUser){
        this.userId = pmsUser.getUserId();
        this.userPw = pmsUser.getUserPw();
        this.userName = pmsUser.getUserName();
        this.userPhoneNumber = pmsUser.getUserPhoneNumber();
    }

}
