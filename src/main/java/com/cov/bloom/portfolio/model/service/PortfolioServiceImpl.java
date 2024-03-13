package com.cov.bloom.portfolio.model.service;

import com.cov.bloom.common.exception.PortfolioRegistException;
import com.cov.bloom.common.exception.ThumbnailRegistException;
import com.cov.bloom.portfolio.model.dao.PortfolioMapper;
import com.cov.bloom.portfolio.model.dto.AttachmentDTO;
import com.cov.bloom.portfolio.model.dto.PortfolioDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioMapper mapper;

    public PortfolioServiceImpl(PortfolioMapper mapper){
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void registThumbnail(PortfolioDTO thumbnail) throws ThumbnailRegistException {
        System.out.println("PortfolioServiceImpl : registThumbnail");
        int result = 0;

        int boardResult = mapper.insertPortfolio(thumbnail);
        System.out.println("mapper.insertPortfolio 다녀왔대요");

        List<AttachmentDTO> attachmentList = thumbnail.getAttachmentDTOList();

        for(int i = 0; i < attachmentList.size(); i++){
            attachmentList.get(i).setRefPortNo(thumbnail.getPortNo());
            attachmentList.get(i).setFileNo(thumbnail.getPortNo() + "_FILE_" + i);
        }

        int attachmentResult = 0;
        for(int i = 0; i < attachmentList.size(); i++){
            attachmentResult += mapper.insertAttachment(attachmentList.get(i));
        }

        if(!(boardResult > 0 && attachmentResult == attachmentList.size())) {
            throw new ThumbnailRegistException("사진 게시판 등록에 실패하였습니다.");
        }
    }

    @Override
    @Transactional
    public void registPortfolio(PortfolioDTO portfolio) throws PortfolioRegistException {
        int result = mapper.insertPortfolio(portfolio);

        if(!(result >0)){
            throw new PortfolioRegistException("게시글 등록에 실패하였습니다.");
        }
    }

}
