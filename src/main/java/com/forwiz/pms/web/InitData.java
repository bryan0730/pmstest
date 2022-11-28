package com.forwiz.pms.web;

import com.forwiz.pms.domain.organization.entity.Organization;
import com.forwiz.pms.domain.organization.service.OrganizationService;
import com.forwiz.pms.domain.rank.dto.SaveRankRequest;
import com.forwiz.pms.domain.rank.entity.UserRank;
import com.forwiz.pms.domain.rank.service.RankService;
import com.forwiz.pms.domain.user.dto.Role;
import com.forwiz.pms.domain.user.entity.PmsUser;
import com.forwiz.pms.domain.user.repository.PmsUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitData {

    private final OrganizationService organizationService;
    private final RankService rankService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PmsUserRepository userRepository;

    @PostConstruct
    @Transactional
    public void init(){
    	if (userRepository.findAll().size()==0) {
        Organization organization = Organization.builder()
                .organizationName("DEFAULT")
                .organizationCode("A0001")
                .organizationDelete(false)
                .build();
        Organization saveOrganization = organizationService.save(organization);

        UserRank saveRank = rankService.saveRank(new SaveRankRequest(saveOrganization.getOrganizationId(), "DEFAULT", 1));

        PmsUser pmsUser = PmsUser.builder()
                .userId("admin")
                .userPw(passwordEncoder.encode("1234"))
                .userName(Role.ROLE_ADMIN.getDescription())
                .auth(Role.ROLE_ADMIN)
                .userOrganizationName(saveRank.getOrganization().getOrganizationName())
                .userRank(saveRank)
                .userPhoneNumber("000-0000-0000")
                .userDeleteYN(false)
                .build()
                ;
        userRepository.save(pmsUser);
    	}
    }
}
