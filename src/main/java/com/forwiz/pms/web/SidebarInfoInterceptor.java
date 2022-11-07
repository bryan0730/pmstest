package com.forwiz.pms.web;

import com.forwiz.pms.domain.organization.dto.OrganizationUsersResponse;
import com.forwiz.pms.domain.organization.service.OrganizationService;
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

        List<OrganizationUsersResponse> infoList = organizationService.selectSideBarUserList();

        try{
            modelAndView.addObject("infoList", infoList);
        }catch (NullPointerException e){
            modelAndView.addObject("infoList", "회원정보 없음");
        }
    }
}
