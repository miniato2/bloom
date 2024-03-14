package com.cov.bloom.order.model.dto;

public class RequestFileDTO implements java.io.Serializable{
    private int fileNo;
    private String filePath;
    private String fileName;
    private int orderNo;

    public RequestFileDTO() {
    }

    public RequestFileDTO(String filePath, String fileName, int orderNo) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.orderNo = orderNo;
    }

    public RequestFileDTO(int fileNo, String filePath, String fileName, int orderNo) {
        this.fileNo = fileNo;
        this.filePath = filePath;
        this.fileName = fileName;
        this.orderNo = orderNo;
    }

    public int getFileNo() {
        return fileNo;
    }

    public void setFileNo(int fileNo) {
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

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public String toString() {
        return "RequestFileDTO{" +
                "fileNo=" + fileNo +
                ", filePath='" + filePath + '\'' +
                ", fileName='" + fileName + '\'' +
                ", orderNo=" + orderNo +
                '}';
    }
}