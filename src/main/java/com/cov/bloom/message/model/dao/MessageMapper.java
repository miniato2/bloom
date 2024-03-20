package com.cov.bloom.message.model.dao;

import com.cov.bloom.member.model.dto.MemberDTO;
import com.cov.bloom.message.model.dto.MessageDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {


    int selectTotalCount();

    List<MessageDTO> sendMessageList(String senderMemberEmail);

    List<MessageDTO> receiveMemberEmail(String receiveMemberEmail);

//    List<MessageDTO> selectAllThumbnailList();
//
//    int incrementMessageCount(Long no);
//
//    MessageDTO selectThumbnailDetail(Long no);
//
//    MemberDTO findMemberId(String memberEmail);

    MemberDTO findEmail(int allMemberNo);

    int regiMessage(MessageDTO messageDTO);

    MemberDTO findMemberId(String memberEmail);
}
