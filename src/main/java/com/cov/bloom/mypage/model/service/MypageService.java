package com.cov.bloom.mypage.model.service;

import com.cov.bloom.member.model.dao.MemberMapper;
import com.cov.bloom.member.model.dto.LoginMemberDTO;
import com.cov.bloom.member.model.dto.MemberDTO;
import com.cov.bloom.mypage.model.dao.MypageMapper;
import com.cov.bloom.order.model.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class MypageService {

    @Value("${image.image-dir}")
    private String IMAGE_DIR;

    @Value("${spring.servlet.multipart.location}")
    private String ROOT_LOCATION;

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
    //판매내역
    @Transactional
    public List<MyOrder> findAllOrderSaleList(String portNo) {

        List<MyOrder> orderList = mypageMapper.findAllOrderSaleList(portNo);

        return orderList;
    }

    //주문상세
    @Transactional
    public OrderDetailDTO getOrderDetail(int orderNo) {

        OrderDetailDTO orderDetail = mypageMapper.getOrderDetail(orderNo);

        return orderDetail;
    }



    //가이드파일 업로드, 상태 업데이트
    @Transactional
    public void submitGuide(OrderDTO order, MultipartFile multipartFile) {

        GuideFileDTO file = new GuideFileDTO();

        String rootLocation = ROOT_LOCATION + IMAGE_DIR;
        String fileUploadDirectory = rootLocation + "/upload/original";

        File directory = new File(fileUploadDirectory);

        if(!directory.exists()){
            directory.mkdirs();
        }

        try{
            String originFileName = multipartFile.getOriginalFilename();
            String ext = originFileName.substring(originFileName.lastIndexOf("."));
            String savedFileName = UUID.randomUUID().toString().replace("-","") + ext;

            //저장
            multipartFile.transferTo(new File(fileUploadDirectory + "/" + savedFileName));

            //저장한 파일 dto 리스트에 담기
            file.setGuideFileName(savedFileName);
            file.setGuideFilePath(fileUploadDirectory);
            file.setOrderNo(order.getOrderNo());
        }catch (IllegalStateException | IOException e){
            // Exception 발생시 파일 삭제
            File deleteFile = new File(fileUploadDirectory + "/" + file.getGuideFileName());

            boolean isDeleted = deleteFile.delete();
        }

        mypageMapper.submitGuide(order);
        mypageMapper.registGuideFile(file);
    }

    public void updateStatus(OrderDTO order) {
        mypageMapper.updateReqStatus(order);
    }

    public void purchaseConfirm(OrderDTO order) {
        mypageMapper.purchaseConfirm(order);
    }
}
