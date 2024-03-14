package com.cov.bloom.mypage.model.dao;

import com.cov.bloom.member.model.dto.LoginMemberDTO;
import com.cov.bloom.order.model.dto.MyOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MypageMapper {

    int updateNickname(String memberName, String nickname);

    int updatePhone(String memberName, String phone);

    LoginMemberDTO findByUsername(String email);

    int updatePW(String memberName, String encpw);

    int deleteMember(String memberName);

    List<MyOrder> findAllOrderList(int memberNo);
}
