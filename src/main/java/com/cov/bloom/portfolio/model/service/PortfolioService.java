package com.cov.bloom.portfolio.model.service;

import com.cov.bloom.common.exception.PortfolioRegistException;
import com.cov.bloom.common.exception.ThumbnailRegistException;
import com.cov.bloom.portfolio.model.dto.PortfolioDTO;

public interface PortfolioService {

    public void registPortfolio(PortfolioDTO port) throws PortfolioRegistException;


    public void registThumbnail(PortfolioDTO thumbnail) throws ThumbnailRegistException;

}
