package com.cov.bloom.portfolio.model.dto;

public class AttachmentDTO {

    private String fileNo;       // 파일번호       / DB : file_no
    private String filePath;    // 파일경로     / DB : file_path
    private String fileName;    // 원본 파일 이름     / DB : file_name
    private String refPortNo;      // 포트폴리오 번호     / DB : port_no
    private String fileType;    // 파일 타입 (TITLE / CONTENT)  / DB : file_type
    private String thumbnailPath; // 썸네일 경로     / DB : thumb_path


    public AttachmentDTO() {
    }

    public AttachmentDTO(String fileNo, String refPortNo, String fileName, String filePath, String fileType, String thumbnailPath) {
        this.fileNo = fileNo;
        this.refPortNo = refPortNo;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.thumbnailPath = thumbnailPath;
    }

    public String getFileNo() {
        return fileNo;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    public String getRefPortNo() {
        return refPortNo;
    }

    public void setRefPortNo(String refPortNo) {
        this.refPortNo = refPortNo;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    @Override
    public String toString() {
        return "AttachmentDTO{" +
                "fileNo=" + fileNo +
                ", refPortNo=" + refPortNo +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileType='" + fileType + '\'' +
                ", thumbnailPath='" + thumbnailPath + '\'' +
                '}';
    }
}
