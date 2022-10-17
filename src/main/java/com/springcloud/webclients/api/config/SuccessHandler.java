package com.springcloud.webclients.api.config;

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
        log.info("Custom SuccessHandler - username: {}", username);
        String groupname = principal.getMyUser().getUserGroup();

        HttpSession session = request.getSession();
        session.setAttribute("username", username);
        session.setAttribute("groupname", groupname);

        response.sendRedirect("/pms");
    }
}
