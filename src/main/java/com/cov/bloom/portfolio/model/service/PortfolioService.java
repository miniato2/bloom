package com.cov.bloom.portfolio.model.service;

import com.cov.bloom.common.exception.*;
import com.cov.bloom.common.paging.SelectCriteria;
import com.cov.bloom.portfolio.model.dto.AttachmentDTO;
import com.cov.bloom.portfolio.model.dto.OptionDTO;
import com.cov.bloom.portfolio.model.dto.PortfolioDTO;

import java.util.List;
import java.util.Map;

public interface PortfolioService {


    /* 포트폴리오 & 사진 등록 */
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

    /* 멤버번호로 포트폴리오번호 조회*/
    PortfolioDTO findPortnoByMemberNo(String memberNo);

    /* 파일번호로 파일삭제 */
    boolean deleteByFileNo(String fileNo) throws FileDeleteException;

    /* 파일번호로 파일정보 조회 */
    AttachmentDTO getFileInfo(String fileNo);

    /* 포트폴리오 수정 */
    void updatePortfolio(PortfolioDTO portfolio) throws ThumbnailRegistException;

    /* 옵션 수정 */
    void updateOption(List<OptionDTO> optionDTOList) throws OptionRegistException;

    /* 포트폴리오번호로 기존 파일정보 조회 */
    List<AttachmentDTO> getOriginalfile(String portNo);

    /* 포트폴리오 삭제*/
    void deletePortfolio(String portNo) throws DeletePortfolioException;
}
