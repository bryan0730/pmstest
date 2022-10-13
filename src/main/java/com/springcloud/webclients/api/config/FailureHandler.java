package com.springcloud.webclients.api.config;

import com.springcloud.webclients.api.util.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class FailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("FailHandler : {}", exception.getMessage());
        log.info("FailHandler : {}", exception.getClass().getSimpleName());

        String exceptionClassName = exception.getClass().getSimpleName();
        ErrorCode ec = ErrorCode.getErrorCode(exceptionClassName);

        HttpSession session = request.getSession();
        session.setAttribute("errmsg", ec.getErrMsg());

        response.sendRedirect("/login-page");
    }
}
