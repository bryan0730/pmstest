package com.springcloud.webclients.api.config;

import com.springcloud.webclients.api.controller.Role;
import com.springcloud.webclients.api.dto.MyUserDetails;
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

        log.info("Custom SuccessHandler : {}", this);

        RequestCache rec = new HttpSessionRequestCache();
        SavedRequest sr = rec.getRequest(request,response);

//        log.info("Custom SuccessHandler RedirectUrl : {}", sr.getRedirectUrl());

        MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        String groupname = principal.getMyUser().getOrganization().getOrganizationName();
        Role role = principal.getMyUser().getAuth();

        log.info("Custom SuccessHandler - username: {}", username);

        //해당 코드에서 "LazyInitializationException: could not initialize proxy no Session" 발생
        //LAZY, EAGER, 영속성 컨텍스트에 대한 이해 필요
        //일단은 MyUser의 organization의 FetchType를 LAZY -> EAGER로 바꾸어서 해결

        HttpSession session = request.getSession();
        session.setAttribute("username", username);
        session.setAttribute("groupname", groupname);
        session.setAttribute("role", role.getDescription());

        response.sendRedirect("/pms");
    }
}
