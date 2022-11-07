package com.forwiz.pms.web;

import com.forwiz.pms.domain.menusetting.service.MenuMapper;
import com.forwiz.pms.domain.menusetting.dto.MenuUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final SidebarInfoInterceptor sidebarInfoInterceptor;

    @Bean
    public MenuMapper menuMapper(){
        MenuMapper menuMapper = new MenuMapper();
        menuMapper.put("MenuUrl", MenuUrl.class);

        return menuMapper;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sidebarInfoInterceptor)
                .addPathPatterns("/pms/*", "/admin/*")
                .excludePathPatterns(
                        "/css/**", "/js/**", "/assets/**"
                )
                ;
    }
}
