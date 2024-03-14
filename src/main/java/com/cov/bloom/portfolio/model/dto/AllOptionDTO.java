package com.cov.bloom.portfolio.model.dto;

public class AllOptionDTO implements  java.io.Serializable{

    private int optionPrice1;   // 1번 옵션 가격
    private String optionInfo1; // 1번 옵션 설명
    private int optionFix1;     // 1번 옵션 수정횟수
    private String optionDt1;   // 1번 옵션 작업기간

    private int optionPrice2;   // 2번 옵션 가격
    private String optionInfo2; // 2번 옵션 설명
    private int optionFix2;     // 2번 옵션 수정횟수
    private String optionDt2;   // 2번 옵션 작업기간

    private int optionPrice3;   // 3번 옵션 가격
    private String optionInfo3; // 3번 옵션 설명
    private int optionFix3;     // 3번 옵션 수정횟수
    private String optionDt3;   // 3번 옵션 작업기간

    public AllOptionDTO() {
    }

    public AllOptionDTO(int optionPrice1, String optionInfo1, int optionFix1, String optionDt1, int optionPrice2, String optionInfo2, int optionFix2, String optionDt2, int optionPrice3, String optionInfo3, int optionFix3, String optionDt3) {
        this.optionPrice1 = optionPrice1;
        this.optionInfo1 = optionInfo1;
        this.optionFix1 = optionFix1;
        this.optionDt1 = optionDt1;
        this.optionPrice2 = optionPrice2;
        this.optionInfo2 = optionInfo2;
        this.optionFix2 = optionFix2;
        this.optionDt2 = optionDt2;
        this.optionPrice3 = optionPrice3;
        this.optionInfo3 = optionInfo3;
        this.optionFix3 = optionFix3;
        this.optionDt3 = optionDt3;
    }

    public int getOptionPrice1() {
        return optionPrice1;
    }

    public void setOptionPrice1(int optionPrice1) {
        this.optionPrice1 = optionPrice1;
    }

    public String getOptionInfo1() {
        return optionInfo1;
    }

    public void setOptionInfo1(String optionInfo1) {
        this.optionInfo1 = optionInfo1;
    }

    public int getOptionFix1() {
        return optionFix1;
    }

    public void setOptionFix1(int optionFix1) {
        this.optionFix1 = optionFix1;
    }

    public String getOptionDt1() {
        return optionDt1;
    }

    public void setOptionDt1(String optionDt1) {
        this.optionDt1 = optionDt1;
    }

    public int getOptionPrice2() {
        return optionPrice2;
    }

    public void setOptionPrice2(int optionPrice2) {
        this.optionPrice2 = optionPrice2;
    }

    public String getOptionInfo2() {
        return optionInfo2;
    }

    public void setOptionInfo2(String optionInfo2) {
        this.optionInfo2 = optionInfo2;
    }

    public int getOptionFix2() {
        return optionFix2;
    }

    public void setOptionFix2(int optionFix2) {
        this.optionFix2 = optionFix2;
    }

    public String getOptionDt2() {
        return optionDt2;
    }

    public void setOptionDt2(String optionDt2) {
        this.optionDt2 = optionDt2;
    }

    public int getOptionPrice3() {
        return optionPrice3;
    }

    public void setOptionPrice3(int optionPrice3) {
        this.optionPrice3 = optionPrice3;
    }

    public String getOptionInfo3() {
        return optionInfo3;
    }

    public void setOptionInfo3(String optionInfo3) {
        this.optionInfo3 = optionInfo3;
    }

    public int getOptionFix3() {
        return optionFix3;
    }

    public void setOptionFix3(int optionFix3) {
        this.optionFix3 = optionFix3;
    }

    public String getOptionDt3() {
        return optionDt3;
    }

    public void setOptionDt3(String optionDt3) {
        this.optionDt3 = optionDt3;
    }

    @Override
    public String toString() {
        return "AllOptionDTO{" +
                "optionPrice1=" + optionPrice1 +
                ", optionInfo1='" + optionInfo1 + '\'' +
                ", optionFix1=" + optionFix1 +
                ", optionDt1='" + optionDt1 + '\'' +
                ", optionPrice2=" + optionPrice2 +
                ", optionInfo2='" + optionInfo2 + '\'' +
                ", optionFix2=" + optionFix2 +
                ", optionDt2='" + optionDt2 + '\'' +
                ", optionPrice3=" + optionPrice3 +
                ", optionInfo3='" + optionInfo3 + '\'' +
                ", optionFix3=" + optionFix3 +
                ", optionDt3='" + optionDt3 + '\'' +
                '}';
    }
}
