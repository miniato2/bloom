package com.cov.bloom.portfolio.model.service;

import com.cov.bloom.common.exception.OptionRegistException;
import com.cov.bloom.common.exception.PortfolioRegistException;
import com.cov.bloom.common.exception.ThumbnailRegistException;
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

    /* 사진 등록 */
    @Override
    @Transactional
    public void registThumbnail(PortfolioDTO thumbnail) throws ThumbnailRegistException {
        System.out.println("***** PortfolioServiceImpl : registThumbnail 들어왔대요");
        int result = 0;

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

    /* 포트폴리오 등록 */
    @Override
    @Transactional
    public void registPortfolio(PortfolioDTO portfolio) throws PortfolioRegistException {
        int result = mapper.insertPortfolio(portfolio);

        if(!(result >0)){

            throw new PortfolioRegistException("게시글 등록에 실패하였습니다.");
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



}
