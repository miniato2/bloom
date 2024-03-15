package com.cov.bloom.order.model.dto;

import java.util.List;

public class OrderDetailDTO implements java.io.Serializable{ //주문상세
    private int orderNo;
    private String requestCon;
    private String optionNo;
    private String optionDt;
    private int optionFix;
    private int optionPrice;
    private char requestStatus;
    private List<RequestFileDTO> requestFile;
    private List<GuideFileDTO> guideFile;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(int orderNo, String requestCon, String optionNo, String optionDt, int optionFix, int optionPrice, char requestStatus, List<RequestFileDTO> requestFile, List<GuideFileDTO> guideFile) {
        this.orderNo = orderNo;
        this.requestCon = requestCon;
        this.optionNo = optionNo;
        this.optionDt = optionDt;
        this.optionFix = optionFix;
        this.optionPrice = optionPrice;
        this.requestStatus = requestStatus;
        this.requestFile = requestFile;
        this.guideFile = guideFile;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public String getRequestCon() {
        return requestCon;
    }

    public void setRequestCon(String requestCon) {
        this.requestCon = requestCon;
    }

    public String getOptionNo() {
        return optionNo;
    }

    public void setOptionNo(String optionNo) {
        this.optionNo = optionNo;
    }

    public String getOptionDt() {
        return optionDt;
    }

    public void setOptionDt(String optionDt) {
        this.optionDt = optionDt;
    }

    public int getOptionFix() {
        return optionFix;
    }

    public void setOptionFix(int optionFix) {
        this.optionFix = optionFix;
    }

    public int getOptionPrice() {
        return optionPrice;
    }

    public void setOptionPrice(int optionPrice) {
        this.optionPrice = optionPrice;
    }

    public char getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(char requestStatus) {
        this.requestStatus = requestStatus;
    }

    public List<RequestFileDTO> getRequestFile() {
        return requestFile;
    }

    public void setRequestFile(List<RequestFileDTO> requestFile) {
        this.requestFile = requestFile;
    }

    public List<GuideFileDTO> getGuideFile() {
        return guideFile;
    }

    public void setGuideFile(List<GuideFileDTO> guideFile) {
        this.guideFile = guideFile;
    }

    @Override
    public String toString() {
        return "OrderDetailDTO{" +
                "orderNo=" + orderNo +
                ", requestCon='" + requestCon + '\'' +
                ", optionNo='" + optionNo + '\'' +
                ", optionDt='" + optionDt + '\'' +
                ", optionFix=" + optionFix +
                ", optionPrice=" + optionPrice +
                ", requestStatus=" + requestStatus +
                ", requestFile=" + requestFile +
                ", guideFile=" + guideFile +
                '}';
    }
}
