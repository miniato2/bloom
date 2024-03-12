package com.cov.bloom.order.model.dto;

public class RequestFileDTO implements java.io.Serializable{
    private String fileNo;
    private String filePath;
    private String fileName;
    private String orderNo;

    public RequestFileDTO() {
    }

    public RequestFileDTO(String filePath, String fileName, String orderNo) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.orderNo = orderNo;
    }

    public RequestFileDTO(String fileNo, String filePath, String fileName, String orderNo) {
        this.fileNo = fileNo;
        this.filePath = filePath;
        this.fileName = fileName;
        this.orderNo = orderNo;
    }

    public String getFileNo() {
        return fileNo;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public String toString() {
        return "RequestFileDTO{" +
                "fileNo='" + fileNo + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileName='" + fileName + '\'' +
                ", orderNo=" + orderNo +
                '}';
    }
}