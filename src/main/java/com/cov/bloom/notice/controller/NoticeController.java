package com.cov.bloom.notice.controller;

import com.cov.bloom.notice.model.dto.NoticeDTO;
import com.cov.bloom.notice.model.service.NoticeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService){
        this.noticeService = noticeService;

    }

    @GetMapping("/main")
    public String notice(Model model){
        List<NoticeDTO> noticeList = noticeService.getNotice();
        model.addAttribute("noticeList",noticeList);

        return "content/notice/notice";




    }

    @GetMapping("/create")
    public String create(){
        return "content/notice/noticeCreate";
    }

    @PostMapping("/create")
    public String createNotice(NoticeDTO noticeDTO){


       NoticeDTO newNotice = noticeDTO;

        noticeService.insertNotice(newNotice);

        return "redirect:/notice/main";
//        return "redirect:/content/notice/notice";


    }






}
