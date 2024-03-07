package com.cov.bloom.member.model.dto;

public class MemberDTO implements java.io.Serializable{

    private int no;
    private String email;
    private String password;
    private String nickname;
    private String name;
    private String phone;
    private char auth;
    private char status;

    public MemberDTO() {
    }

    public MemberDTO(int no, String email, String password, String nickname, String name, String phone, char auth, char status) {
        this.no = no;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.phone = phone;
        this.auth = auth;
        this.status = status;
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

    public char getAuth() {
        return auth;
    }

    public void setAuth(char auth) {
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
        return "MemberDTO{" +
                "no=" + no +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", name='" + name + '\'' +
                ", phone=" + phone +
                ", auth=" + auth +
                ", status=" + status +
                '}';
    }
}
