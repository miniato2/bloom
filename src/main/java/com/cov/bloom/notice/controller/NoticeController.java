package com.cov.bloom.notice.controller;

import com.cov.bloom.notice.model.dto.NoticeDTO;
import com.cov.bloom.notice.model.service.NoticeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;
    private final HttpSession session;

    public NoticeController(NoticeService noticeService, HttpSession session){
        this.session = session;
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

    @GetMapping("/detail")
    public ModelAndView detailNotice(ModelAndView mv, @RequestParam("date")String date){


        NoticeDTO notice = noticeService.findNotice(date);

            mv.setViewName("content/notice/noticeDetail");
            mv.addObject("notice",notice);
            return mv;

    }

//    @PostMapping("/update")
//    public String updateNotice(String con){
//        int no = (Integer) session.getAttribute("no");
//
//        System.out.println(no);
//
//        int result = noticeService.updateNotice(no,con);
//
//
//            if (result>0) {
//                return "content/notice/notice";
//            }
//            else{
//                return "content/notice/notice";
//            }
//
//    }


@PostMapping("/update")
public String updateNotice(Integer no, String con){


    System.out.println(no);

    int result = noticeService.updateNotice(no,con);


    if (result>0) {
        return "redirect:/notice/main";
    }
    else{
        return "redirect:/notice/main";
    }

}
@GetMapping("/delete")
    public String deleteNotice(Integer no){



    System.out.println(no);
    int result = noticeService.deleteNotice(no);

    if (result>0) {
        return "redirect:/notice/main";
    }
    else{
        return "redirect:/notice/main";
    }


 }




}
