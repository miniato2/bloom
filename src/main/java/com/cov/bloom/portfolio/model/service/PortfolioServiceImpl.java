package com.cov.bloom.portfolio.model.service;

import com.cov.bloom.common.exception.*;
import com.cov.bloom.common.paging.SelectCriteria;
import com.cov.bloom.member.model.dto.MemberDTO;
import com.cov.bloom.portfolio.model.dao.PortfolioMapper;
import com.cov.bloom.portfolio.model.dto.AttachmentDTO;
import com.cov.bloom.portfolio.model.dto.OptionDTO;
import com.cov.bloom.portfolio.model.dto.PortfolioDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioMapper mapper;

    public PortfolioServiceImpl(PortfolioMapper mapper){
        this.mapper = mapper;
    }


    /* 게시글 전체 수 조회 */
    @Override
    public int selectTotalCount(Map<String, String> searchMap) {

        int result = mapper.selectTotalCount(searchMap);

        return result;
    }

    // 포트폴리오 전체 조회
    @Override
    public List<PortfolioDTO> selectPortList(SelectCriteria selectCriteria) {
        List<PortfolioDTO> portfolioList = mapper.selectPortfolioList(selectCriteria);

        return portfolioList;
    }

    /* 멤버ID 조회 */
    @Override
    public int findMemberId(String email) {
       int result = 0;
        MemberDTO member = mapper.findMemberId(email);
       result = member.getNo();

       return result;
    }

    /* 포트폴리오 상세 조회 */
    @Override
    public PortfolioDTO selectPortDetail(String portNo) {
        PortfolioDTO portDetail = null;

        portDetail = mapper.selectPortfolioDetail(portNo);

        return portDetail;
    }


    /* 포트폴리오 & 사진 등록 */
    @Override
    @Transactional
    public void registThumbnail(PortfolioDTO thumbnail) throws ThumbnailRegistException {
        System.out.println("***** PortfolioServiceImpl : registThumbnail 들어왔대요");

        // 포트폴리오 등록
        int boardResult = mapper.insertPortfolio(thumbnail);
        System.out.println("***** mapper.insertPortfolio 다녀왔대요");


        // 파일 등록
        List<AttachmentDTO> attachmentList = thumbnail.getAttachmentDTOList();

        for(int i = 0; i < attachmentList.size(); i++){
            attachmentList.get(i).setRefPortNo(thumbnail.getPortNo());
            attachmentList.get(i).setFileNo(thumbnail.getPortNo() + "_FILE_" + i);
        }

        int attachmentResult = 0;
        for(int i = 0; i < attachmentList.size(); i++){
            attachmentResult += mapper.insertAttachment(attachmentList.get(i));
        }
        System.out.println("***** mapper.insertAttachment 다녀왔대요");


        if(!(boardResult > 0 && attachmentResult == attachmentList.size())) {
            throw new ThumbnailRegistException("포트폴리오 등록에 실패하였습니다.");
        }
    }


    /* 옵션 등록 */
    @Override
    @Transactional
    public void registOption(List<OptionDTO> optionDtoList) throws OptionRegistException {

        int registOptionResult = 0;

        for(int i = 0; i < optionDtoList.size(); i++){
            registOptionResult += mapper.registOption(optionDtoList.get(i));
        }
        System.out.println("***** mapper.registOption 다녀왔대요");

        if(registOptionResult != optionDtoList.size()){
            throw new OptionRegistException("옵션 등록에 실패하였습니다.");
        }

    }

    /* 포트폴리오넘버 조회 */
    @Override
    public PortfolioDTO findPortnoByMemberNo(String memberNo) {

        PortfolioDTO portfolio = null;

        portfolio = mapper.findPortnoByMemberNo(memberNo);

        return portfolio;

    }

    /* 기존 파일 지우기 */
    @Override
    @Transactional
    public boolean deleteByFileNo(String fileNo) throws FileDeleteException {
        int result = 0;

        result = mapper.deleteByFileNo(fileNo);

        if(!(result > 0)){
            throw new FileDeleteException("파일 삭제에 실패했습니다.");

        }

        return result > 0 ? true : false;
    }

    /* 파일번호로 파일 정보 불러오기 */
    @Override
    public AttachmentDTO getFileInfo(String fileNo) {
        AttachmentDTO attachmentDTO = mapper.getFileInfo(fileNo);

        return attachmentDTO;
    }

    /* 포트폴리오 수정 */
    @Override
    @Transactional
    public void updatePortfolio(PortfolioDTO portfolio) throws ThumbnailRegistException {
        System.out.println("***** PortfolioServiceImpl : updatePortfolio 들어왔대요");
        int result = 0;

        // 포트폴리오 수정
        int boardResult = mapper.updatePortfolio(portfolio);
        System.out.println("***** mapper.updatePortfolio 다녀왔대요");


        // 파일 등록
        List<AttachmentDTO> attachmentList = portfolio.getAttachmentDTOList();

        for(int i = 0; i < attachmentList.size(); i++){
            attachmentList.get(i).setRefPortNo(portfolio.getPortNo());
        }

        int attachmentResult = 0;
        for(int i = 0; i < attachmentList.size(); i++){
            attachmentResult += mapper.insertAttachment(attachmentList.get(i));
        }
        System.out.println("***** mapper.insertAttachment 다녀왔대요");


        if(!(boardResult > 0 && attachmentResult == attachmentList.size())) {
            throw new ThumbnailRegistException("포트폴리오 등록에 실패하였습니다.");
        }


    }

    /* 옵션 수정 */
    @Override
    @Transactional
    public void updateOption(List<OptionDTO> optionDTOList) throws OptionRegistException {
        int registOptionResult = 0;

        for(int i = 0; i < optionDTOList.size(); i++){

            registOptionResult += mapper.updateOption(optionDTOList.get(i));
        }
        System.out.println("***** mapper.updateOption 다녀왔대요");

        if(registOptionResult != optionDTOList.size()){
            throw new OptionRegistException("옵션 등록에 실패하였습니다.");
        }
    }

    /* 포트폴리오번호로 기존 파일 목록 조회  */
    @Override
    public List<AttachmentDTO> getOriginalfile(String portNo) {
        List<AttachmentDTO> attachmentDTOList = mapper.getOriginalfiles(portNo);


        return attachmentDTOList;
    }

    /* 포트폴리오 삭제 */
    @Override
    @Transactional
    public void deletePortfolio(String portNo) throws DeletePortfolioException {
        int portresult = 0;
        int fileresult = 0;
        int optionresult = 0;

        //파일 삭제
        fileresult = mapper.deleteFiles(portNo);
        System.out.println("파일 삭제 : " + fileresult);

        //옵션 삭제
        optionresult = mapper.deleteOptions(portNo);
        System.out.println("옵션 삭제 : " + optionresult);


        //포트폴리오 삭제
        portresult = mapper.deletePortfolio(portNo);
        System.out.println("포트폴리오 삭제 : " + portresult);


        if(!(portresult > 0 && fileresult > 0 && optionresult > 0)){
            throw new DeletePortfolioException("포트폴리오 삭제에 실패했습니다.");
        }
    }


}
