package com.springcloud.webclients.api.entity;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORGANIZATION_SEQ_GENERATOR")
    private Long organizationId;

    @Column(length = 50, nullable = false)
    private String organizationName;

    @Column(length = 50, nullable = false)
    private String organizationCode;

    @Column(nullable = false)
    private Boolean organizationDelete;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
    private List<MyUser> myUsers = new ArrayList<>();

    public void updateDelYN(boolean delYN){
        this.organizationDelete = delYN;
    }
}
