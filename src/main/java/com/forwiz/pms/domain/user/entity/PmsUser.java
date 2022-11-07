package com.forwiz.pms.domain.user.entity;

import com.forwiz.pms.domain.message.entity.Message;
import com.forwiz.pms.domain.organization.entity.Organization;
import com.forwiz.pms.domain.user.dto.Role;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@SequenceGenerator(name="USER_SEQ_GENERATOR", sequenceName = "USER_SEQ", allocationSize = 1)
public class PmsUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GENERATOR")
    private Long id;

    @Column(length = 50, nullable = false)
    private String userId;

    @Column(length = 200, nullable = false)
    private String userPw;

    @Column(length = 50, nullable = false)
    private String userName;

    @ManyToOne(fetch = FetchType.LAZY)
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

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private List<Message> messages = new ArrayList<>();

    public void updateDelYN(boolean delYN) {
        this.userDeleteYN = delYN;
    }
}
