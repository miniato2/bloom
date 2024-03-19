package com.cov.bloom.order.model.dto;

public class MyOrder implements java.io.Serializable {
    private int orderNo;             //주문 번호
    private String portTitle;        //포폴제목
    private String memberNick;       //닉네임 (주문내역에는 판매자 닉네임, 판매내역에서는 구매자 닉네임)
    private String orderDt;          //신청일
    private String orderFinalDt;     //마감일
    private int price;               //가격
    private String requestStatus;     //상태

    public MyOrder() {
    }

    public MyOrder(int orderNo, String portTitle, String memberNick, String orderDt, String orderFinalDt, int price, String requestStatus) {
        this.orderNo = orderNo;
        this.portTitle = portTitle;
        this.memberNick = memberNick;
        this.orderDt = orderDt;
        this.orderFinalDt = orderFinalDt;
        this.price = price;
        this.requestStatus = requestStatus;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public String getPortTitle() {
        return portTitle;
    }

    public void setPortTitle(String portTitle) {
        this.portTitle = portTitle;
    }

    public String getMemberNick() {
        return memberNick;
    }

    public void setMemberNick(String memberNick) {
        this.memberNick = memberNick;
    }

    public String getOrderDt() {
        return orderDt;
    }

    public void setOrderDt(String orderDt) {
        this.orderDt = orderDt;
    }

    public String getOrderFinalDt() {
        return orderFinalDt;
    }

    public void setOrderFinalDt(String orderFinalDt) {
        this.orderFinalDt = orderFinalDt;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    @Override
    public String toString() {
        return "MyOrder{" +
                "orderNo=" + orderNo +
                ", portTitle='" + portTitle + '\'' +
                ", memberNick='" + memberNick + '\'' +
                ", orderDt='" + orderDt + '\'' +
                ", orderFinalDt='" + orderFinalDt + '\'' +
                ", price=" + price +
                ", requestStatus='" + requestStatus + '\'' +
                '}';
    }
}

