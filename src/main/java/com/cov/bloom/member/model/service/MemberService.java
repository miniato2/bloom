package com.cov.bloom.member.model.service;

import com.cov.bloom.member.model.dao.MemberMapper;
import com.cov.bloom.member.model.dto.LoginMemberDTO;
import com.cov.bloom.member.model.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class MemberService {

    @Autowired
    private final MemberMapper memberMapper;


    @Autowired
    private PasswordEncoder passwordEncoder;

    public MemberService(MemberMapper memberMapper){
        this.memberMapper=memberMapper;
    }

    @Transactional
    public void signinMember(MemberDTO newMember){
        newMember.setPassword(passwordEncoder.encode(newMember.getPassword()));

        memberMapper.signinMember(newMember);
    }



    public LoginMemberDTO findByUsername(String email){
        LoginMemberDTO login = memberMapper.findByUsername(email);

        System.out.println("aaa: " + passwordEncoder.encode(login.getPassword()));


        if(!Objects.isNull((login))){
            return login;

        }else{
            return null;
        }
    }

    public int duplicationEmail(String email) {


       int result= memberMapper.duplicationEmail(email);
        System.out.println("결과값은 ?" + result);

        return result;
    }


    public String findID(String name, String phone) {

        String result = memberMapper.findID(name,phone);

        return result;
    }

    public int findPW(String memberName, String encpw) {
        int result = memberMapper.findPW(memberName,encpw);
        return result;
    }
}
