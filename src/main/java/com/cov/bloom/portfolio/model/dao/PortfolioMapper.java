package com.cov.bloom.portfolio.model.dao;

import com.cov.bloom.common.paging.SelectCriteria;
import com.cov.bloom.member.model.dto.MemberDTO;
import com.cov.bloom.portfolio.model.dto.AttachmentDTO;
import com.cov.bloom.portfolio.model.dto.OptionDTO;
import com.cov.bloom.portfolio.model.dto.PortfolioDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PortfolioMapper {

    int selectTotalCount(Map<String, String> searchMap);
    List<PortfolioDTO> selectPortfolioList(SelectCriteria selectCriteria);

    int insertBoard(PortfolioDTO port);

    int insertPortfolio(PortfolioDTO thumbnail);

    int insertAttachment(AttachmentDTO attachmentDTO);


    int registOption(OptionDTO optionDTO);

    MemberDTO findMemberId(String email);

}
