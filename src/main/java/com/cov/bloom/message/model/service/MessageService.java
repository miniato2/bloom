package com.cov.bloom.message.model.service;

import com.cov.bloom.common.exception.MessageRegistException;
import com.cov.bloom.common.paging.SelectCriteria;
import com.cov.bloom.member.model.dto.MemberDTO;
import com.cov.bloom.message.model.dto.MessageDTO;

import java.util.List;

public interface MessageService {
    int selectTotalCount();

    List<MessageDTO> selectMessageList();

    MessageDTO selectMessagDetail(long no);

    List<MessageDTO> selectAllThumbnailList();

    MessageDTO selectThumbnailDetail(Long no);

    int selectTotalSentCount(int cmemberNo);

    List<MessageDTO> selectSentMessageList(int cmemberNo, SelectCriteria selectCriteria);

    int selectTotalReceivedCount(int cmemberNo);

    List<MessageDTO> selectReceivedMessageList(int cmemberNo, SelectCriteria receivedCriteria);

    MemberDTO findMemberId(String memberEmail);

    String findMemberEmail(int senderMember);

    int registMessage(MessageDTO messageDTO) throws MessageRegistException;
}
