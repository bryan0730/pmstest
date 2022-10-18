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
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long organizationId;

    @Column(length = 50, nullable = false)
    private String organizationName;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
    private List<MyUser> myUsers = new ArrayList<>();
}
