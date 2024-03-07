package com.cov.bloom.member.model.dao;

import com.cov.bloom.member.model.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {


    void signinMember(MemberDTO newMember);



}
