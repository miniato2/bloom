package com.cov.bloom.config;


import com.cov.bloom.common.MemberRole;
import com.cov.bloom.config.handler.AuthFailHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatchers;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthFailHandler authFailHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();

    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());

        //정적 리소스에 대한 요청은 그 내용은 시큐리티 걸지 않고 처리하겠다.
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth ->{
            auth.requestMatchers("auth/login","/","main","member/signin","/member/email","/member/verify",
                    "/portfolio/regist","/img/**").permitAll();
            auth.requestMatchers("/auth/*","/mypage/*").hasAnyAuthority(MemberRole.c.getRole(),MemberRole.s.getRole(),MemberRole.m.getRole());
         

            auth.anyRequest().permitAll(); //권한 전부줌


        }).formLogin(login -> {
            login.loginPage("/auth/login");
            login.usernameParameter("email");
            login.passwordParameter("password");
            login.defaultSuccessUrl("/auth/success",true);
            login.failureHandler(authFailHandler);


        }).logout(logout ->{
            logout.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"));
            logout.deleteCookies("JSEESIONID");
            logout.invalidateHttpSession(true);
            logout.logoutSuccessUrl("/main");

        }).sessionManagement(session ->{
            session.maximumSessions(1);
            session.invalidSessionUrl("/");

        }).csrf(csrf -> csrf.disable());
        //csrf 토큰을 요청에 포함하는 경우 가끔 안되는경우 있어서 그것만 풀고 진행해보자
        //크로스 사이트 무뭐뭠 그런 그런건가봐요

        return http.build();
    }

}
