package com.cov.bloom.notice.model.service;


import com.cov.bloom.member.model.dao.MemberMapper;
import com.cov.bloom.notice.model.dao.NoticeMapper;
import com.cov.bloom.notice.model.dto.NoticeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {

    @Autowired
    private final NoticeMapper noticeMapper;

    public NoticeService(NoticeMapper noticeMapper){
        this.noticeMapper=noticeMapper;

    }


    public void insertNotice(NoticeDTO newNotice) {

        noticeMapper.insertNotice(newNotice);



    }

    public List<NoticeDTO> getNotice() {

       List<NoticeDTO> noticeList = noticeMapper.getNotice();

        return noticeList;
    }
}
