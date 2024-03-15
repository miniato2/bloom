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

    /* 포트폴리오 총 갯수 조회*/
    int selectTotalCount(Map<String, String> searchMap);

    /* 포트폴리오 목록 조회*/
    List<PortfolioDTO> selectPortfolioList(SelectCriteria selectCriteria);

    /* 포트폴리오 등록 */
    int insertPortfolio(PortfolioDTO thumbnail);

    /* 사진 등록 */
    int insertAttachment(AttachmentDTO attachmentDTO);

    /* 옵션 등록 */
    int registOption(OptionDTO optionDTO);

    /* 멤버 넘버 찾기*/
    MemberDTO findMemberId(String email);

    /* 포트폴리오 상세 조회*/
    PortfolioDTO selectPortfolioDetail(String portNo);
}
