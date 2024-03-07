package com.cov.bloom.member.model.service;

import com.cov.bloom.member.model.dao.MemberMapper;
import com.cov.bloom.member.model.dto.MemberDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberMapper memberMapper;

    public MemberService(MemberMapper memberMapper){
        this.memberMapper=memberMapper;
    }

    @Transactional
    public void signinMember(MemberDTO newMember){
        memberMapper.signinMember(newMember);
    }
}
