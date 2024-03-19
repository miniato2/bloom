package com.cov.bloom.notice.model.service;


import com.cov.bloom.member.model.dao.MemberMapper;
import com.cov.bloom.notice.model.dao.NoticeMapper;
import com.cov.bloom.notice.model.dto.NoticeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoticeService {

    @Autowired
    private final NoticeMapper noticeMapper;

    public NoticeService(NoticeMapper noticeMapper){
        this.noticeMapper=noticeMapper;

    }

    @Transactional
    public void insertNotice(NoticeDTO newNotice) {

        noticeMapper.insertNotice(newNotice);



    }

    public List<NoticeDTO> getNotice() {

       List<NoticeDTO> noticeList = noticeMapper.getNotice();

        return noticeList;
    }

    @Transactional
    public int updateNotice(int no, String con) {
        Map updateInfo = new HashMap<>();
        updateInfo.put("no",no);
        updateInfo.put("con",con);

        int result = noticeMapper.updateNotice(updateInfo);
        return result;
    }

    public int deleteNotice(int no) {

        int result = noticeMapper.deleteNotice(no);
        return result;

    }

    public NoticeDTO findNotice(String date) {
        NoticeDTO result = noticeMapper.findNotice(date);
        return result;
    }
}
