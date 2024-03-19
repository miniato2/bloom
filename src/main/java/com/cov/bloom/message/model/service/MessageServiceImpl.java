package com.cov.bloom.message.model.service;

import com.cov.bloom.message.model.dao.MessageMapper;
import com.cov.bloom.message.model.dto.MessageDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageMapper mapper;
    public MessageServiceImpl(MessageMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public int selectTotalCount() {

        int result = mapper.selectTotalCount();

        return result;
    }

    @Override
    public List<MessageDTO> selectMessageList() {
        List<MessageDTO> messageList = mapper.selectMessageList();
        return messageList;
    }

    @Override
    public MessageDTO selectMessagDetail(long no) {
        MessageDTO messageDetail = null;
        return messageDetail;
    }

//    @Override
//    public List<MessageDTO> selectAllThumbnailList() {
//        List<MessageDTO> thumbnailList = mapper.selectAllThumbnailList();
//        return thumbnailList;
//    }


//    @Override
//    public MessageDTO selectThumbnailDetail(Long no) {
//        MessageDTO thumbnailDetail = null;
//
//        int result = mapper.incrementMessageCount(no);
//
//        if(result > 0) {
//            thumbnailDetail = mapper.selectThumbnailDetail(no);
//        }
//
//        return thumbnailDetail;
//    }


}
