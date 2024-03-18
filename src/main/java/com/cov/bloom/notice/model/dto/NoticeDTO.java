package com.cov.bloom.notice.model.dto;

public class NoticeDTO implements java.io.Serializable{

    private int no;
    private String title;
    private String con;
    private String date;


    public NoticeDTO() {
    }

    public NoticeDTO(int no, String title, String con, String date) {
        this.no = no;
        this.title = title;
        this.con = con;
        this.date = date;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "NoticeDTO{" +
                "no=" + no +
                ", title='" + title + '\'' +
                ", con='" + con + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}

