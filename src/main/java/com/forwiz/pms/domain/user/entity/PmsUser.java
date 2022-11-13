package com.forwiz.pms.domain.user.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.forwiz.pms.domain.board.entity.Board;
import com.forwiz.pms.domain.message.entity.Message;
import com.forwiz.pms.domain.organization.entity.Organization;
import com.forwiz.pms.domain.user.dto.Role;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@SequenceGenerator(name="USER_SEQ_GENERATOR", sequenceName = "USER_SEQ", allocationSize = 1)
public class PmsUser {

    @Id
    @Column
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

    @OneToMany(mappedBy = "pmsUser", fetch = FetchType.LAZY)
    private List<Board> boards = new ArrayList<>();
    
    public void updateDelYN(boolean delYN) {
        this.userDeleteYN = delYN;
    }

    public boolean hasSameId(Long id){
        return this.id.equals(id);
    }
}
