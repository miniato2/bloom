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

    public void registPortfolio(PortfolioDTO port) throws PortfolioRegistException;


    public void registThumbnail(PortfolioDTO thumbnail) throws ThumbnailRegistException;

    public void registOption(List<OptionDTO> optionDtoList) throws OptionRegistException;

    int selectTotalCount(Map<String, String> searchMap);

    List<PortfolioDTO> selectPortList(SelectCriteria selectCriteria);

    int findMemberId(String email);
}
