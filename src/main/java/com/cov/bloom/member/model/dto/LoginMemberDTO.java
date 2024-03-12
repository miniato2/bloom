package com.cov.bloom.member.model.dto;

import com.cov.bloom.common.MemberRole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginMemberDTO implements java.io.Serializable{
    private int no;
    private String email;
    private String password;
    private String nickname;
    private String name;
    private String phone;
    private MemberRole auth;
    private char status;

    public LoginMemberDTO() {
    }

    public LoginMemberDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginMemberDTO(int no, String email, String password, String nickname, String name, String phone, MemberRole auth, char status) {
        this.no = no;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.phone = phone;
        this.auth = auth;
        this.status = status;
    }

    public List<String> getRole(){
        if(this.auth.getRole().length()>0){
            return Arrays.asList(this.auth.getRole().split(","));
        }

        return new ArrayList<>();
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public MemberRole getAuth() {
        return auth;
    }

    public void setAuth(MemberRole auth) {
        this.auth = auth;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LoginMemberDTO{" +
                "no=" + no +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", auth=" + auth +
                ", status=" + status +
                '}';
    }
}
