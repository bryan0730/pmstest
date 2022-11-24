package com.forwiz.pms.domain.rank.entity;

import com.forwiz.pms.domain.organization.entity.Organization;
import com.forwiz.pms.domain.user.entity.PmsUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name="RANK_SEQ_GENERATOR", sequenceName = "RANK_SEQ", allocationSize = 1)
public class UserRank {

    //application.properties에 spring.jpa.properties.hibernate.default_batch_fetch_size=1000 추가

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RANK_SEQ_GENERATOR")
    private Long rankId;

    private String rankName;

    private Integer rankWeight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @OneToMany(mappedBy = "userRank")
    private List<PmsUser> pmsUsers = new ArrayList<>();
}
