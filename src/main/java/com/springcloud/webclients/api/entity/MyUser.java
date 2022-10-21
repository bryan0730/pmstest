package com.springcloud.webclients.api.entity;

import com.springcloud.webclients.api.util.Role;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@SequenceGenerator(name="USER_SEQ_GENERATOR", sequenceName = "USER_SEQ", allocationSize = 1)
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GENERATOR")
    private Long id;

    @Column(length = 50, nullable = false)
    private String userId;

    @Column(length = 200, nullable = false)
    private String userPw;

    @Column(length = 50, nullable = false)
    private String userName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private Role auth;

    @Column(nullable = false)
    private String userPhoneNumber;

    @Column(nullable = false)
    private String userRank;

    @Column(nullable = false)
    private Boolean userDeleteYN;
    
    public void updateDeleteYN(boolean delYN){
        this.userDeleteYN = delYN;
    }
}
