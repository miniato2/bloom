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

    /* 멤버번호로 포트폴리오번호 조회 */
    PortfolioDTO findPortnoByMemberNo(String memberNo);

    /* 포트폴리오번호로 기존 파일 목록 조회 */
    List<AttachmentDTO> getOriginalfiles(String portNo);

    /* 파일 번호 별 삭제 */
    int deleteByFileNo(String fileNo);

    /* 파일번호로 기존 파일 정보 조회 */
    AttachmentDTO getFileInfo(String fileNo);

    /* 포트폴리오 수정 */
    int updatePortfolio(PortfolioDTO portfolio);

    /* 옵션 수정 */
    int updateOption(OptionDTO optionDTO);

    /* 포트폴리오 삭제 */
    int deletePortfolio(String portNo);

    /* 파일 삭제 */
    int deleteFiles(String portNo);

    /* 옵션 삭제 */
    int deleteOptions(String portNo);
}
