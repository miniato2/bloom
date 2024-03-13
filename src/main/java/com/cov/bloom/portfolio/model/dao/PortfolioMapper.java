package com.cov.bloom.portfolio.model.dao;

import com.cov.bloom.portfolio.model.dto.AttachmentDTO;
import com.cov.bloom.portfolio.model.dto.PortfolioDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PortfolioMapper {

    int insertBoard(PortfolioDTO port);

    int insertPortfolio(PortfolioDTO thumbnail);

    int insertAttachment(AttachmentDTO attachmentDTO);


}
