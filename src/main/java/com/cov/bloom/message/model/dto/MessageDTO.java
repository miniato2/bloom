package com.cov.bloom.message.model.dto;

public class MessageDTO implements java.io.Serializable{

    private int msgNo;

    private String msgDate;

    private String msgContent;

    private String senderMemberEmail;

    private String recipientMemberEmail;

    public MessageDTO() {
    }

    public MessageDTO(int msgNo, String msgDate, String msgContent, String senderMemberEmail, String recipientMemberNo) {
        this.msgNo = msgNo;
        this.msgDate = msgDate;
        this.msgContent = msgContent;
        this.senderMemberEmail = senderMemberEmail;
        this.recipientMemberEmail = recipientMemberNo;
    }

    public int getMsgNo() {
        return msgNo;
    }

    public void setMsgNo(int msgNo) {
        this.msgNo = msgNo;
    }

    public String getMsgDate() {
        return msgDate;
    }

    public void setMsgDate(String msgDate) {
        this.msgDate = msgDate;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getSenderMemberEmail() {
        return senderMemberEmail;
    }

    public void setSenderMemberEmail(String senderMemberEmail) {
        this.senderMemberEmail = senderMemberEmail;
    }

    public String getRecipientMemberEmail() {
        return recipientMemberEmail;
    }

    public void setRecipientMemberEmail(String recipientMemberEmail) {
        this.recipientMemberEmail = recipientMemberEmail;
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
                "msgNo=" + msgNo +
                ", msgDate='" + msgDate + '\'' +
                ", msgContent='" + msgContent + '\'' +
                ", senderMemberEmail=" + senderMemberEmail +
                ", recipientMemberEmail=" + recipientMemberEmail +
                '}';
    }
}
