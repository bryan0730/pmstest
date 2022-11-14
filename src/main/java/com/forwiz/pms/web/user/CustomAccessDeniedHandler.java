package com.forwiz.pms.web.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.setStatus(HttpStatus.FORBIDDEN.value());

        request.setAttribute("msg", "해당 페이지에 접근 또는 엑세스할 수 있는 권한이 없습니다.");
        request.setAttribute("nextPage", "pms/board/notice");

        request.getRequestDispatcher("/err/denied-page").forward(request,response);
    }
}
