package com.forwiz.pms.common.fixtures;

import com.forwiz.pms.domain.organization.entity.Organization;

public class OrganizationFixtures {

    public static final String 케리스_이름 = "KERIS";
    public static final String 케리스_코드 = "A00001";

    public static final String 포위즈_이름 = "FORWIZ0";
    public static final String 포위즈_코드 = "A00002";

    public static Organization 케리스(){
        return Organization.builder()
                .organizationId(1L)
                .organizationName(케리스_이름)
                .organizationCode(케리스_코드)
                .organizationDelete(false)
                .build();
    }

    public static Organization 포위즈(){
        return Organization.builder()
                .organizationId(2L)
                .organizationName(포위즈_이름)
                .organizationCode(포위즈_코드)
                .organizationDelete(false)
                .build();
    }
}
