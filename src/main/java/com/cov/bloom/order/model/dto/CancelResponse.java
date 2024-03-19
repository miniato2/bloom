package com.cov.bloom.order.model.dto;

import java.util.Date;

//결제 취소
public class CancelResponse {
    private String aid;
    private String tid;
    private String cid;
    private String status;
    private String partner_order_id;
    private String partner_user_id;
    private Amount amount;
    private ApprovedCancelAmount approve_cancel_amount;
    private String item_name;
    private String item_code;
    private Date created_at;
    private Date canceled_at;

    public CancelResponse() {
    }

    public CancelResponse(String aid, String tid, String cid, String status, String partner_order_id, String partner_user_id, Amount amount, ApprovedCancelAmount approve_cancel_amount, String item_name, String item_code, Date created_at, Date canceled_at) {
        this.aid = aid;
        this.tid = tid;
        this.cid = cid;
        this.status = status;
        this.partner_order_id = partner_order_id;
        this.partner_user_id = partner_user_id;
        this.amount = amount;
        this.approve_cancel_amount = approve_cancel_amount;
        this.item_name = item_name;
        this.item_code = item_code;
        this.created_at = created_at;
        this.canceled_at = canceled_at;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public ApprovedCancelAmount getApprove_cancel_amount() {
        return approve_cancel_amount;
    }

    public void setApprove_cancel_amount(ApprovedCancelAmount approve_cancel_amount) {
        this.approve_cancel_amount = approve_cancel_amount;
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

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getCanceled_at() {
        return canceled_at;
    }

    public void setCanceled_at(Date canceled_at) {
        this.canceled_at = canceled_at;
    }

    @Override
    public String toString() {
        return "CancelResponse{" +
                "aid='" + aid + '\'' +
                ", tid='" + tid + '\'' +
                ", cid='" + cid + '\'' +
                ", status='" + status + '\'' +
                ", partner_order_id='" + partner_order_id + '\'' +
                ", partner_user_id='" + partner_user_id + '\'' +
                ", amount=" + amount +
                ", approve_cancel_amount=" + approve_cancel_amount +
                ", item_name='" + item_name + '\'' +
                ", item_code='" + item_code + '\'' +
                ", created_at=" + created_at +
                ", canceled_at=" + canceled_at +
                '}';
    }
}
