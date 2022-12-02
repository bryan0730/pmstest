package com.forwiz.pms.common;

import com.forwiz.pms.domain.user.dto.PmsUserDetails;
import com.forwiz.pms.domain.user.dto.Role;
import com.forwiz.pms.domain.user.entity.PmsUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {


    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser annotation) {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        PmsUserDetails principal = new PmsUserDetails(
                PmsUser.builder().id(1L)
                        .userId("user1")
                        .userName("David")
                        .userPw("1234")
                        .auth(Role.ROLE_ADMIN)
                        .build()
        );
        Authentication auth = new UsernamePasswordAuthenticationToken(principal, "password", principal.getAuthorities());
        securityContext.setAuthentication(auth);
        return securityContext;
    }
}
