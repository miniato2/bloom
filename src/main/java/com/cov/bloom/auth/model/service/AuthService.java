package com.cov.bloom.auth.model.service;

import com.cov.bloom.auth.model.AuthDetails;
import com.cov.bloom.member.model.dto.LoginMemberDTO;
import com.cov.bloom.member.model.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private MemberService memberService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LoginMemberDTO login = memberService.findByUsername(email);


        System.out.println(login);

        if(Objects.isNull(login)){

            throw new UsernameNotFoundException("해당하는 회원 정보가 존재하지 않습니다.");

        }

        return new AuthDetails(login);
    }
    public LoginMemberDTO getMember(String email) {

        LoginMemberDTO member = memberService.findByUsername(email);

        return member;

    }
}
