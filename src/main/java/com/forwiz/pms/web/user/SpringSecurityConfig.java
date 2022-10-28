package com.forwiz.pms.web.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import javax.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity
//@RequiredArgsConstructor
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
                .antMatchers("/login-page","/sign-up", "/go-sign", "/no").anonymous()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/pms/**").hasAnyRole("ADMIN","USER")
                .anyRequest().authenticated()
                .and()

//                .authenticationProvider(authenticationProvider())


                .formLogin()
                .loginPage("/login-page")
//                .defaultSuccessUrl("/pms") /* 설정할 경우 SuccessHandler가 작동 못함 */
                .loginProcessingUrl("/process-login")
                .successHandler(successHandler())
                .failureHandler(failureHandler())
                .usernameParameter("userid")
                .passwordParameter("userpw")
                .permitAll()
                .and()
                
                .logout()
                .logoutUrl("/logout")
                .addLogoutHandler((request, response, authentication) -> {
                    HttpSession session = request.getSession();
                    session.invalidate();
                })
                .logoutSuccessUrl("/login-page")
                .deleteCookies("JSESSIONID")
                .and()

                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .and()

                .sessionManagement()
                .sessionFixation().changeSessionId() //어차피 서블릿 3.1이상 버전에서는 defalut 값
                .invalidSessionUrl("/login-page")
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true) // true : 초과된 세션 사용자 로그인 허용, 기존 사용자 로그아웃 & false : 초과된 사용자 로그인 불가
                .expiredUrl("/login-page")
                ;

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler(){
        return new SuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler failureHandler(){
        return new FailureHandler();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){return new CustomAccessDeniedHandler(); }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        System.out.println("configuration.getAuthenticationManager() = " + configuration.getAuthenticationManager());
        return configuration.getAuthenticationManager();
    }

    /* AuthenticationProvider를 implements한 CustomProvider가 있는 경우에는
    *  시큐리티가 Provider를 못찾아서 NotFoundProviderException을 낸다. 때문에
    *  있다면 아래와 같이 빈 등록(DaoProvider나 CustomProvider) 등록 후
    *  위의 HttpSecurity 체인 설정에 authenticationProvider(authProvider())해줘야 한다.
    *   */
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//
//        authProvider.setUserDetailsService(myUserDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder());
//
//        return authProvider;
//    }

    /*
    @Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(this.userService);
		provider.setPasswordEncoder(this.customPasswordEncoder());

		return provider;
	}
     */

}
