package com.springcloud.webclients.jpah2.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
public class TbUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userCode;

    @Column(length = 50, nullable = false)
    private String userId;

    @Column(length = 50, nullable = false)
    private String userName;
}
