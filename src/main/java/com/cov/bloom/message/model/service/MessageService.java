package com.cov.bloom.message.model.service;

import com.cov.bloom.message.model.dto.MessageDTO;

import java.util.List;

public interface MessageService {
    int selectTotalCount();

    List<MessageDTO> selectMessageList();

    MessageDTO selectMessagDetail(long no);

//    List<MessageDTO> selectAllThumbnailList();

//    MessageDTO selectThumbnailDetail(Long no);
}
