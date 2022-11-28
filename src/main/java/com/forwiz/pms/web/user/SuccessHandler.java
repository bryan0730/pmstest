package com.forwiz.pms.web.user;

import com.forwiz.pms.domain.user.dto.Role;
import com.forwiz.pms.domain.user.dto.PmsUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class SuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        PmsUserDetails principal = (PmsUserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        String groupname = principal.getPmsUser().getUserRank().getOrganization().getOrganizationName();
        Role role = principal.getPmsUser().getAuth();

        HttpSession session = request.getSession();
        session.setAttribute("id", principal.getPmsUser().getId());
        session.setAttribute("username", username);
        session.setAttribute("groupname", groupname);
        session.setAttribute("role", role.getDescription());

        response.sendRedirect("/pms/board/notice");
    }
}
