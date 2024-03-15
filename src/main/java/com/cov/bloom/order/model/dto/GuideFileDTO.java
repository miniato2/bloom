package com.cov.bloom.order.model.dto;

public class GuideFileDTO implements java.io.Serializable{
    private int guideFileNo;
    private String guideFilePath;
    private String guideFileName;
    private int orderNo;

    public GuideFileDTO() {
    }

    public GuideFileDTO(int guideFileNo, String guideFilePath, String guideFileName, int orderNo) {
        this.guideFileNo = guideFileNo;
        this.guideFilePath = guideFilePath;
        this.guideFileName = guideFileName;
        this.orderNo = orderNo;
    }

    public int getGuideFileNo() {
        return guideFileNo;
    }

    public void setGuideFileNo(int guideFileNo) {
        this.guideFileNo = guideFileNo;
    }

    public String getGuideFilePath() {
        return guideFilePath;
    }

    public void setGuideFilePath(String guideFilePath) {
        this.guideFilePath = guideFilePath;
    }

    public String getGuideFileName() {
        return guideFileName;
    }

    public void setGuideFileName(String guideFileName) {
        this.guideFileName = guideFileName;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public String toString() {
        return "GuideFileDTO{" +
                "guideFileNo=" + guideFileNo +
                ", guideFilePath='" + guideFilePath + '\'' +
                ", guideFileName='" + guideFileName + '\'' +
                ", orderNo=" + orderNo +
                '}';
    }
}
