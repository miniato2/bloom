package com.cov.bloom.order.model.dto;

public class OptionDTO implements java.io.Serializable{

    private String optionNo;           //옵션번호
    private String portNo;             //포트폴리오번호
    private int optionPrice;        //옵션가격
    private String optionDt;        //작업시간
    private int optionFix;          //수정횟수
    private String optionInfo;      //옵션설명

    public OptionDTO() {
    }

    public OptionDTO(String optionNo, String portNo, int optionPrice, String optionDt, int optionFix, String optionInfo) {
        this.optionNo = optionNo;
        this.portNo = portNo;
        this.optionPrice = optionPrice;
        this.optionDt = optionDt;
        this.optionFix = optionFix;
        this.optionInfo = optionInfo;
    }

    public String getOptionNo() {
        return optionNo;
    }

    public void setOptionNo(String optionNo) {
        this.optionNo = optionNo;
    }

    public String getPortNo() {
        return portNo;
    }

    public void setPortNo(String portNo) {
        this.portNo = portNo;
    }

    public int getOptionPrice() {
        return optionPrice;
    }

    public void setOptionPrice(int optionPrice) {
        this.optionPrice = optionPrice;
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

    public String getOptionInfo() {
        return optionInfo;
    }

    public void setOptionInfo(String optionInfo) {
        this.optionInfo = optionInfo;
    }

    @Override
    public String toString() {
        return "OptionDTO{" +
                "optionNo='" + optionNo + '\'' +
                ", portNo='" + portNo + '\'' +
                ", optionPrice=" + optionPrice +
                ", optionDt='" + optionDt + '\'' +
                ", optionFix=" + optionFix +
                ", optionInfo='" + optionInfo + '\'' +
                '}';
    }
}
