package com.springcloud.webclients.api.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        /*
        이전 버전 WebSecurityConfigureAdapter의
        WebSecurity web에서 ignore 해줬던 url들은 http에서 permitAll 해주는 것을 권장하고 있다.(Spring docs)
        Spring Security에서 해당 경로를 무시하는 것이기 때문에 CSRF, XSS, Clickjacking 등에서 보호하지 않는다. 따라서 이 취약점으로부터 보호하려면 HttpSecurity에서 permitAll을 해야한다.
         */
        http
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/admin/**").hasRole(Level.getEnumLevel(1).name())
                .antMatchers("/forwiz/**").hasRole(Level.getEnumLevel(2).name())
                .antMatchers("/keris/**").hasRole(Level.getEnumLevel(3).name())
                .anyRequest().authenticated()
                .and()

                .formLogin()
                .loginPage("/login.html")
                .defaultSuccessUrl("/pms")
                .loginProcessingUrl("/process-login")
                .successHandler(successHandler())
                .failureHandler(failureHandler())
                .usernameParameter("userid")
                .passwordParameter("userpw")
                .permitAll()
                .and()

                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID")
                .and()

                .sessionManagement()
                .sessionFixation().changeSessionId() //어차피 서블릿 3.1이상 버전에서는 defalut 값
                .invalidSessionUrl("/login")
                .maximumSessions(2)
                .maxSessionsPreventsLogin(true) // true : 초과된 세션 사용자 로그인 허용, 기존 사용자 로그아웃 & false : 초과된 사용자 로그인 불가
                .expiredUrl("/longin")


                .httpBasic(withDefaults())
                ;

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler(){
        return null;
    }

    @Bean
    public AuthenticationFailureHandler failureHandler(){
        return null;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
