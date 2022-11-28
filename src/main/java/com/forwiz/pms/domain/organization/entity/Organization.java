package com.forwiz.pms.domain.organization.entity;

import com.forwiz.pms.domain.organization.exception.DeleteAdminOrganizationException;
import com.forwiz.pms.domain.rank.entity.UserRank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SequenceGenerator(name="ORGANIZATION_SEQ_GENERATOR", sequenceName = "ORGANIZATION_SEQ", allocationSize = 1)
public class Organization {

    @Id
    @Column(name = "organization_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORGANIZATION_SEQ_GENERATOR")
    private Long organizationId;

    @Column(length = 50, nullable = false)
    private String organizationName;

    @Column(length = 50, nullable = false)
    private String organizationCode;

    @Column(nullable = false)
    private Boolean organizationDelete;

    @OneToMany(mappedBy = "organization")
    private List<UserRank> userRanks = new ArrayList<>();

    private void verifyAdminOrganization(){
        if (this.organizationName.equals("DEFAULT")){
            throw new DeleteAdminOrganizationException("관리자 조직은 삭제할 수 없습니다.");
        }
    }

    public void updateDelYN(boolean delYN){
        verifyAdminOrganization();
        this.organizationDelete = delYN;
    }
}
