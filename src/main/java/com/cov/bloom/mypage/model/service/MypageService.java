package com.cov.bloom.mypage.model.service;

import com.cov.bloom.member.model.dao.MemberMapper;
import com.cov.bloom.member.model.dto.LoginMemberDTO;
import com.cov.bloom.member.model.dto.MemberDTO;
import com.cov.bloom.mypage.model.dao.MypageMapper;
import com.cov.bloom.order.model.dto.MyOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class MypageService {

    @Autowired
    private final MypageMapper mypageMapper;


    @Autowired
    private PasswordEncoder passwordEncoder;

    public MypageService(MypageMapper mypageMapper){
        this.mypageMapper=mypageMapper;
    }



    public LoginMemberDTO findByUsername(String email){
        LoginMemberDTO login = mypageMapper.findByUsername(email);

//        System.out.println("aaa: " + passwordEncoder.encode(login.getPassword()));


        if(!Objects.isNull((login))){
            return login;

        }else{
            return null;
        }
    }



    @Transactional
    public int updateNickname(String memberName, String nickname) {
        int result = mypageMapper.updateNickname(memberName,nickname);

        return result;
    }

    @Transactional
    public int updatePhone(String memberName, String phone) {
        int result = mypageMapper.updatePhone(memberName,phone);
        return result;
    }

    public int updatePW(String memberName, String encpw) {
        int result = mypageMapper.updatePW(memberName,encpw);
        return result;
    }

    public int deleteMember(String memberName) {
        int result = mypageMapper.deleteMember(memberName);
        return result;
    }
    public int getSales(String memberName) {
        int result = mypageMapper.getSales(memberName);
        return result;
    }

    //내 주문내역 불러오기
    @Transactional
    public List<MyOrder> findAllOrderList(int memberNo) {

        List<MyOrder> orderlist = mypageMapper.findAllOrderList(memberNo);
        System.out.println(orderlist);

        return orderlist;
    }


}
