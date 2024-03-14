package com.cov.bloom.portfolio.model.dto;

public class OptionDTO implements java.io.Serializable {

    private String optionNo;    // 옵션 번호 / DB : option_no
    private String portNo;      // 포트폴리오 번호 / DB : port_no
    private int optionPrice;    // 가격 / DB : option_price
    private String optionInfo;  // 옵션설명 / DB : option_info
    private int optionFix;      // 수정횟수 / DB : option_fix
    private String optionDt;    // 작업기간 / DB : option_dt

    public OptionDTO() {
    }

    public OptionDTO(String optionNO, String portNo, int optionPrice, String optionInfo, int optionFix, String optionDt) {
        this.optionNo = optionNO;
        this.portNo = portNo;
        this.optionPrice = optionPrice;
        this.optionInfo = optionInfo;
        this.optionFix = optionFix;
        this.optionDt = optionDt;
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

    public String getOptionInfo() {
        return optionInfo;
    }

    public void setOptionInfo(String optionInfo) {
        this.optionInfo = optionInfo;
    }

    public int getOptionFix() {
        return optionFix;
    }

    public void setOptionFix(int optionFix) {
        this.optionFix = optionFix;
    }

    public String getOptionDt() {
        return optionDt;
    }

    public void setOptionDt(String optionDt) {
        this.optionDt = optionDt;
    }

    @Override
    public String toString() {
        return "OptionDTO{" +
                "optionNO='" + optionNo + '\'' +
                ", portNo='" + portNo + '\'' +
                ", optionPrice=" + optionPrice +
                ", optionInfo='" + optionInfo + '\'' +
                ", oprionFix=" + optionFix +
                ", optionDt='" + optionDt + '\'' +
                '}';
    }
}
