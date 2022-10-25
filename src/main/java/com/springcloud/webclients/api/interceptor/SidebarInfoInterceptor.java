package com.springcloud.webclients.api.interceptor;

import com.springcloud.webclients.api.dto.AllOrganizationResponse;
import com.springcloud.webclients.api.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class SidebarInfoInterceptor implements HandlerInterceptor {

    private final OrganizationService organizationService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        List<AllOrganizationResponse> infoList = organizationService.findAll();
        modelAndView.addObject("infoList", infoList);
    }
}
