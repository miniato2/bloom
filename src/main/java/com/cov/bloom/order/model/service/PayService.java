package com.cov.bloom.order.model.service;

import com.cov.bloom.mypage.model.dao.MypageMapper;
import com.cov.bloom.order.model.dao.OrderDAO;
import com.cov.bloom.order.model.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Service
public class PayService {

    private RestTemplate restTemplate;
    private ReadyResponse readyResponse;
    private CancelResponse cancelReponse;

    private OrderDAO orderDAO;
    private MypageMapper mypageMapper;

    private final String cid = "TC0ONETIME";
    private final String key = "DEVFEA509038BE8883CA03AEF9B404496B5E685F";

    public PayService (OrderDAO orderDAO, MypageMapper mypageMapper){
        this.orderDAO = orderDAO;
        this.mypageMapper = mypageMapper;
        this.restTemplate = new RestTemplate();
        this.readyResponse = new ReadyResponse();
        this.cancelReponse = new CancelResponse();

    }

    private HttpHeaders getHeaders(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "SECRET_KEY " + this.key );
        httpHeaders.set("Content-Type", "application/json;charset=UTF-8");

        return httpHeaders;
    }

    public ReadyResponse readyPay(OrderDTO order, OptionDTO option){

        Map<String, Object> parameter = new LinkedHashMap<>();

        parameter.put("cid", this.cid);                                 //가맹점코드
        parameter.put("partner_order_id", order.getOrderNo());         //주문번호
        parameter.put("partner_user_id", order.getMemberNo() + "");     //회원번호
        parameter.put("item_name", order.getPortNo());                  //상품명
        parameter.put("quantity", 1); //int                             //수량
        parameter.put("total_amount", option.getOptionPrice());//int    //가격
        parameter.put("tax_free_amount", 0);//int                       //비과세금액
        parameter.put("approval_url", "http://localhost:8080/pay/success");
        parameter.put("cancel_url", "http://localhost:8080/main");
        parameter.put("fail_url", "http://localhost:8080/pay/fail");

        ObjectMapper mapper = new ObjectMapper();

        String body = null;
        try {
            body = mapper.writeValueAsString(parameter);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpEntity<String> requestEntity = new HttpEntity<>(body, getHeaders());

        readyResponse = restTemplate.postForObject( //readyDTO
                "https://open-api.kakaopay.com/online/v1/payment/ready"
                ,requestEntity
                ,ReadyResponse.class
        );

        return readyResponse;
    }

    @Transactional
    public ApproveResponse approvePay(String pgToken, OrderDTO order) {

        Map<String, Object> parameter = new LinkedHashMap<>();

        parameter.put("cid", this.cid);
        parameter.put("tid", readyResponse.getTid());               //결제 고유번호 준비api 응답에 포함되어있음
        parameter.put("partner_order_id", order.getOrderNo());      //주문번호
        parameter.put("partner_user_id", order.getMemberNo() + ""); //회원 id
        parameter.put("pg_token", pgToken);

        ObjectMapper mapper = new ObjectMapper();

        String body = null;

        try {
            body = mapper.writeValueAsString(parameter);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpEntity<String> requestEntity = new HttpEntity<>(body, getHeaders());

        ApproveResponse approveResponse = restTemplate.postForObject(
                "https://open-api.kakaopay.com/online/v1/payment/approve"
                ,requestEntity
                ,ApproveResponse.class
        );

        return approveResponse;
    }

    @Transactional
    public void registOrder(OrderDTO order, List<RequestFileDTO> files) {
        orderDAO.registOrder(order); //주문 등록
        orderDAO.registOrderFile(files); //파일 저장
    }

    public int checkOrderNo() {
        return orderDAO.checkOrderNo();
    }

    //결제 취소
    @Transactional
    public void cancelPay(OrderDTO order) {

        Map<String, Object> orderInfo = orderDAO.selectOrder(order.getOrderNo()); //주문번호로 정보조회

        Map<String, Object> parameter = new LinkedHashMap<>();
        parameter.put("cid", this.cid);
        parameter.put("tid", orderInfo.get("order_tid"));
        parameter.put("cancel_amount", orderInfo.get("option_price"));
        parameter.put("cancel_tax_free_amount", 0);

        ObjectMapper mapper = new ObjectMapper();

        String body = null;
        try {
            body = mapper.writeValueAsString(parameter);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpEntity<String> requestEntity = new HttpEntity<>(body, getHeaders());

        cancelReponse = restTemplate.postForObject(
                "https://open-api.kakaopay.com/online/v1/payment/cancel"
                ,requestEntity
                ,CancelResponse.class
        );

        if(cancelReponse != null){
            order.setRequestStatus("C");
            mypageMapper.updateReqStatus(order); //주문 상태 C (cancel)로 업데이트
        }
    }


}