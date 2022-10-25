package com.springcloud.webclients.api.config;

import com.springcloud.webclients.api.interceptor.SidebarInfoInterceptor;
import com.springcloud.webclients.api.service.MenuMapper;
import com.springcloud.webclients.api.util.MenuUrl;
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
//                .excludePathPatterns()
                ;
    }
}
