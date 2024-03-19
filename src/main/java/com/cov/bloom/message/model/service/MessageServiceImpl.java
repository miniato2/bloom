package com.cov.bloom.message.model.service;

import com.cov.bloom.common.exception.MessageRegistException;
import com.cov.bloom.common.paging.SelectCriteria;
import com.cov.bloom.member.model.dto.MemberDTO;
import com.cov.bloom.message.model.dao.MessageMapper;
import com.cov.bloom.message.model.dto.MessageDTO;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public List<MessageDTO> selectAllThumbnailList() {
        List<MessageDTO> thumbnailList = mapper.selectAllThumbnailList();
        return thumbnailList;
    }


    @Override
    public MessageDTO selectThumbnailDetail(Long no) {
        MessageDTO thumbnailDetail = null;

        int result = mapper.incrementMessageCount(no);

        if(result > 0) {
            thumbnailDetail = mapper.selectThumbnailDetail(no);
        }

        return thumbnailDetail;
    }

    @Override
    public int selectTotalSentCount(int cmemberNo) {
        return 0;
    }

    @Override
    public List<MessageDTO> selectSentMessageList(int cmemberNo, SelectCriteria selectCriteria) {
        return null;
    }

    @Override
    public int selectTotalReceivedCount(int cmemberNo) {
        return 0;
    }

    @Override
    public List<MessageDTO> selectReceivedMessageList(int cmemberNo, SelectCriteria receivedCriteria) {
        return null;
    }

    @Override
    public MemberDTO findMemberId(String memberEmail) {
        MemberDTO member = mapper.findMemberId(memberEmail);
        return member;
    }

    @Override
    public String findMemberEmail(int allMemberNo) {
        String Email = "";

        Email = mapper.findEmail(allMemberNo);

        return Email;
    }

    @Override
    @Transactional
    public int registMessage(MessageDTO messageDTO) throws MessageRegistException {

        int result = 0;
        result = mapper.regiMessage(messageDTO);

        if (!(result > 0)){

            throw new MessageRegistException("메세지 전송 실패");
        }

        return result;
    }


}
