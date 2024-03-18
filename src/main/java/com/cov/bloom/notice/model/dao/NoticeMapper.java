package com.cov.bloom.notice.model.dao;


import com.cov.bloom.notice.model.dto.NoticeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {
    void insertNotice(NoticeDTO newNotice);

    List<NoticeDTO> getNotice();
}
