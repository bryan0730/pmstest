package com.forwiz.pms.web.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class FailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        log.info("FailHandler : {}", exception.getMessage());
        log.info("FailHandler : {}", exception.getClass().getSimpleName());

        String exceptionClassName = exception.getClass().getSimpleName();
        ErrorCode ec = ErrorCode.getErrorCode(exceptionClassName);

        HttpSession session = request.getSession();
        session.setAttribute("errmsg", ec.getErrMsg());

        response.sendRedirect("/login-page");
    }
}
