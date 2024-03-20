package com.cov.bloom.config.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;
import java.net.URLEncoder;

@Configuration
public class AuthFailHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = "";

        System.out.println(exception.fillInStackTrace());
        System.out.println(exception.getCause());
        System.out.println(exception.getStackTrace());

        if(exception instanceof BadCredentialsException){
            errorMessage = "아이디 혹은 비밀번호를 확인해주세요";
        }else if(exception instanceof InternalAuthenticationServiceException){
            errorMessage = "서버에서 오류가 발생하였습니다. 관리자에게 문의해 주세요";
        }else if(exception instanceof UsernameNotFoundException){
            errorMessage = "존재하지 않는 ID입니다.";
        }
        else if(exception instanceof AuthenticationFailureHandler) {
            errorMessage = "인증요청이 거부 되었습니다 .";
        }
        else if(exception instanceof AccountExpiredException) {
            errorMessage = "탈퇴한 회원입니다 .";
        }
        else {
            errorMessage="알 수 없는 오류로 로그인 요청을 처리할 수 없습니다. 관리자에게 문의해 주세요.";
        }

        errorMessage = URLEncoder.encode(errorMessage,"UTF-8");

        System.out.println("최종 에러메세지" + errorMessage);

        setDefaultFailureUrl("/auth/fail?message=" + errorMessage);

        super.onAuthenticationFailure(request, response, exception);

    }
}
