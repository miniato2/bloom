package com.cov.bloom.portfolio.model.service;

import com.cov.bloom.common.exception.OptionRegistException;
import com.cov.bloom.common.exception.PortfolioRegistException;
import com.cov.bloom.common.exception.ThumbnailRegistException;
import com.cov.bloom.common.paging.SelectCriteria;
import com.cov.bloom.portfolio.model.dto.OptionDTO;
import com.cov.bloom.portfolio.model.dto.PortfolioDTO;

import java.util.List;
import java.util.Map;

public interface PortfolioService {

    /* 포트폴리오 등록 */
    public void registPortfolio(PortfolioDTO port) throws PortfolioRegistException;

    /* 사진 등록 */
    public void registThumbnail(PortfolioDTO thumbnail) throws ThumbnailRegistException;

    /* 옵션 등록 */
    public void registOption(List<OptionDTO> optionDtoList) throws OptionRegistException;

    /* 포트폴리오 게시물 총 갯수 조회 */
    public int selectTotalCount(Map<String, String> searchMap);

    /* 포트폴리오 게시물 목록 조회 */
    public List<PortfolioDTO> selectPortList(SelectCriteria selectCriteria);

    /* 멤버ID 조회 */
    public int findMemberId(String email);

    /* 포트폴리오 상세조회 */
    public PortfolioDTO selectPortDetail(String portNo);
}
