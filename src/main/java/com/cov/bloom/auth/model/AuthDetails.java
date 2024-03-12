package com.cov.bloom.auth.model;

import com.cov.bloom.member.model.dto.LoginMemberDTO;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


public class AuthDetails implements UserDetails {

    private LoginMemberDTO loginMemberDTO;

    public AuthDetails(LoginMemberDTO loginMemberDTO){
        this.loginMemberDTO = loginMemberDTO;
    }

    public LoginMemberDTO getLoginMemberDTO() {
        return loginMemberDTO;
    }

    public void setLoginMemberDTO(LoginMemberDTO loginMemberDTO) {
        this.loginMemberDTO = loginMemberDTO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //권한을 가지고 오겠다!
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        loginMemberDTO.getRole().forEach(role-> authorities.add(() -> role));

        return authorities;
    }

    @Override
    public String getPassword() {
        return loginMemberDTO.getPassword();
    }

    @Override
    public String getUsername() {
       return loginMemberDTO.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
       return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}