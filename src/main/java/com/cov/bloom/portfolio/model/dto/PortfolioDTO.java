package com.cov.bloom.portfolio.model.dto;

import com.cov.bloom.member.model.dto.MemberDTO;

import java.util.List;

public class PortfolioDTO implements java.io.Serializable {

    private String portNo;      // 포트폴리오번호 / DB : port_no
    private String portTitle;   // 포트폴리오제목 / DB : port_title
    private String portCon;     // 포트폴리오내용 / DB : port_con
    private String portContactDt;   //연락가능시간 / DB : port_contactdt
    private String memberInfo;      //판매자 소개 / DB : member_info
    private int memberNo;           //회원번호 / DB : member_no
    private String writeDt;         // 작성일시 / DB : write_dt
    private List<AttachmentDTO> attachmentDTOList;  // 사진 리스트

    private MemberDTO memberNickname;   // 회원 - 회원 닉네임
    private List<OptionDTO> optionList;      // 옵션 - 옵션 가격

    private int minOptionPrice;         // 가장 낮은 가격의 옵션 가격



    public PortfolioDTO() {
    }

    public PortfolioDTO(String portNo, String portTitle, String portCon, String portContactDt, String memberInfo, int memberNo, String writeDt, List<AttachmentDTO> attachmentDTOList) {
        this.portNo = portNo;
        this.portTitle = portTitle;
        this.portCon = portCon;
        this.portContactDt = portContactDt;
        this.memberInfo = memberInfo;
        this.memberNo = memberNo;
        this.writeDt = writeDt;
        this.attachmentDTOList = attachmentDTOList;
    }

    public PortfolioDTO(String portNo, String portTitle, String portCon, String portContactDt, String memberInfo, int memberNo, String writeDt, List<AttachmentDTO> attachmentDTOList, MemberDTO memberNickname, List<OptionDTO> optionList, int minOptionPrice) {
        this.portNo = portNo;
        this.portTitle = portTitle;
        this.portCon = portCon;
        this.portContactDt = portContactDt;
        this.memberInfo = memberInfo;
        this.memberNo = memberNo;
        this.writeDt = writeDt;
        this.attachmentDTOList = attachmentDTOList;
        this.memberNickname = memberNickname;
        this.optionList = optionList;
        this.minOptionPrice = minOptionPrice;
    }

    public String getPortNo() {
        return portNo;
    }

    public void setPortNo(String portNo) {
        this.portNo = portNo;
    }

    public String getPortTitle() {
        return portTitle;
    }

    public void setPortTitle(String portTitle) {
        this.portTitle = portTitle;
    }

    public String getPortCon() {
        return portCon;
    }

    public void setPortCon(String portCon) {
        this.portCon = portCon;
    }

    public String getPortContactDt() {
        return portContactDt;
    }

    public void setPortContactDt(String portContactDt) {
        this.portContactDt = portContactDt;
    }

    public String getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(String memberInfo) {
        this.memberInfo = memberInfo;
    }

    public int getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(int memberNo) {
        this.memberNo = memberNo;
    }

    public String getWriteDt() {
        return writeDt;
    }

    public void setWriteDt(String writeDt) {
        this.writeDt = writeDt;
    }

    public List<AttachmentDTO> getAttachmentDTOList() {
        return attachmentDTOList;
    }

    public void setAttachmentDTOList(List<AttachmentDTO> attachmentDTOList) {
        this.attachmentDTOList = attachmentDTOList;
    }

    public MemberDTO getMemberNickname() {
        return memberNickname;
    }

    public void setMemberNickname(MemberDTO memberNickname) {
        this.memberNickname = memberNickname;
    }

    public List<OptionDTO> getOptionPrice() {
        return optionList;
    }

    public void setOptionPrice(List<OptionDTO> optionPrice) {
        this.optionList = optionPrice;
    }

    public List<OptionDTO> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<OptionDTO> optionList) {
        this.optionList = optionList;
    }

    public int getMinOptionPrice() {
        return minOptionPrice;
    }

    public void setMinOptionPrice(int minOptionPrice) {
        this.minOptionPrice = minOptionPrice;
    }

    @Override
    public String toString() {
        return "PortfolioDTO{" +
                "portNo='" + portNo + '\'' +
                ", portTitle='" + portTitle + '\'' +
                ", portCon='" + portCon + '\'' +
                ", portContactDt='" + portContactDt + '\'' +
                ", memberInfo='" + memberInfo + '\'' +
                ", memberNo=" + memberNo +
                ", writeDt='" + writeDt + '\'' +
                ", attachmentDTOList=" + attachmentDTOList +
                ", memberNickname=" + memberNickname +
                ", optionList=" + optionList +
                ", minOptionPrice=" + minOptionPrice +
                '}';
    }
}

