package com.cov.bloom.mypage.model.dao;

import com.cov.bloom.common.paging.SelectCriteria;
import com.cov.bloom.member.model.dto.LoginMemberDTO;
import com.cov.bloom.order.model.dto.GuideFileDTO;
import com.cov.bloom.order.model.dto.MyOrder;
import com.cov.bloom.order.model.dto.OrderDTO;
import com.cov.bloom.order.model.dto.OrderDetailDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MypageMapper {

    int updateNickname(String memberName, String nickname);

    int updatePhone(String memberName, String phone);

    LoginMemberDTO findByUsername(String email);

    int updatePW(String memberName, String encpw);

    int deleteMember(String memberName);

    int getSales(String memberName);

    OrderDetailDTO getOrderDetail(int orderNo); //주문상세

    void updateReqStatus(OrderDTO order); // requestStatus 업데이트

    void registGuideFile(GuideFileDTO file); //가이드파일 저장

    void purchaseConfirm(OrderDTO order);

    void submitGuide(OrderDTO order);

    int selectTotalSale(String portNo);     //판매내역 수 조회

    List<MyOrder> selectSaleList(SelectCriteria selectCriteria); //판매내역조회 (페이징)

    int selectTotalOrder(int memberNo);

    List<MyOrder> selectOrderList(SelectCriteria selectCriteria); //주문내역조회 (페이징)
}
