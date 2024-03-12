package com.cov.bloom.order.model.dto;

import java.sql.Date;

public class ApproveResponse { //결제 승인 응답
    private String aid;                     //요청고유 번호 - 승인/취소 구분된 결제번호
    private String tid;                     //결제 고유 번호 - 승인/취소가 동일한 결제번호
    private String cid;                     //가맹점 코드
    private String partner_order_id;        //가맹점 주문번호, 최대 100자
    private String partner_user_id;         //가맹점 회원 id, 최대 100자
    private Amount amount;                  //결제 금액 정보
    private String item_name;               //상품이름
    private String item_code;               //상품코드
    private Integer quantity;               //수량
    private Date created_at;                //결제 준비 요청 시각
    private Date approved_at;               //결제 승인 시각
    private String payload;                 //결제 승인 요청에 대해 저장한 값, 요청 시 전달된 내용

    public ApproveResponse() {
    }

    public ApproveResponse(String aid, String tid, String cid, String partner_order_id, String partner_user_id, Amount amount, String item_name, String item_code, Integer quantity, Date created_at, Date approved_at, String payload) {
        this.aid = aid;
        this.tid = tid;
        this.cid = cid;
        this.partner_order_id = partner_order_id;
        this.partner_user_id = partner_user_id;
        this.amount = amount;
        this.item_name = item_name;
        this.item_code = item_code;
        this.quantity = quantity;
        this.created_at = created_at;
        this.approved_at = approved_at;
        this.payload = payload;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPartner_order_id() {
        return partner_order_id;
    }

    public void setPartner_order_id(String partner_order_id) {
        this.partner_order_id = partner_order_id;
    }

    public String getPartner_user_id() {
        return partner_user_id;
    }

    public void setPartner_user_id(String partner_user_id) {
        this.partner_user_id = partner_user_id;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getApproved_at() {
        return approved_at;
    }

    public void setApproved_at(Date approved_at) {
        this.approved_at = approved_at;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "ApproveResponse{" +
                "aid='" + aid + '\'' +
                ", tid='" + tid + '\'' +
                ", cid='" + cid + '\'' +
                ", partner_order_id='" + partner_order_id + '\'' +
                ", partner_user_id='" + partner_user_id + '\'' +
                ", amount=" + amount +
                ", item_name='" + item_name + '\'' +
                ", item_code='" + item_code + '\'' +
                ", quantity=" + quantity +
                ", created_at=" + created_at +
                ", approved_at=" + approved_at +
                ", payload='" + payload + '\'' +
                '}';
    }
}