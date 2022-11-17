package com.forwiz.pms.web;

import com.forwiz.pms.domain.menusetting.service.MenuMapper;
import com.forwiz.pms.domain.menusetting.dto.MenuUrl;
import com.forwiz.pms.web.interceptor.FileExtentionChangeInterceptor;
import com.forwiz.pms.web.interceptor.NoticeInterceptor;

import com.forwiz.pms.web.interceptor.SidebarInfoInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final SidebarInfoInterceptor sidebarInfoInterceptor;
    private final NoticeInterceptor noticeInterceptor;
    private final FileExtentionChangeInterceptor fileExtentionChangeInterceptor;

    @Bean
    public MenuMapper menuMapper(){
        MenuMapper menuMapper = new MenuMapper();
        menuMapper.put("MenuUrl", MenuUrl.class);

        return menuMapper;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sidebarInfoInterceptor)
                .order(2)
                .addPathPatterns("/pms/*", "/admin/*", "/pms/message/**", "/pms/board/*")
                .excludePathPatterns(
                        "/css/**", "/js/**", "/assets/**"
                );

        registry.addInterceptor(noticeInterceptor)
                .order(1)
                .addPathPatterns("/pms/*", "/pms/message/**", "/pms/board/*", "/admin/*")
                .excludePathPatterns(
                        "/css/**", "/js/**", "/assets/**"
                );
        registry.addInterceptor(fileExtentionChangeInterceptor)
                .order(3)
                .addPathPatterns("/attach/*")
                .excludePathPatterns("/css/**", "/js/**", "/assets/**");
    }
}
