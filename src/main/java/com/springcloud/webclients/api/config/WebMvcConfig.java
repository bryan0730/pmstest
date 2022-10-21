package com.springcloud.webclients.api.config;

import com.springcloud.webclients.api.service.MenuMapper;
import com.springcloud.webclients.api.util.MenuUrl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public MenuMapper menuMapper(){
        MenuMapper menuMapper = new MenuMapper();
        menuMapper.put("MenuUrl", MenuUrl.class);

        return menuMapper;
    }
}
