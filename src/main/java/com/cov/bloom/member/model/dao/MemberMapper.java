package com.cov.bloom.member.model.dao;

import com.cov.bloom.member.model.dto.LoginMemberDTO;
import com.cov.bloom.member.model.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {


    void signinMember(MemberDTO newMember);

    LoginMemberDTO findByUsername(String email);


    int duplicationEmail(String email);


    String findID(String name, String phone);

    int findPW(String memberName, String encpw);
}
