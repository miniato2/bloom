package com.cov.bloom.message.model.dao;

import com.cov.bloom.message.model.dto.MessageDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {


    int selectTotalCount();

    List<MessageDTO> selectMessageList();

    List<MessageDTO> selectAllThumbnailList();

    int incrementMessageCount(Long no);

    MessageDTO selectThumbnailDetail(Long no);
}
