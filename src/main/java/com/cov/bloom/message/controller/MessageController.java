package com.cov.bloom.message.controller;

import com.cov.bloom.common.paging.Pagenation;
import com.cov.bloom.common.paging.SelectCriteria;
import com.cov.bloom.message.model.dto.MessageDTO;
import com.cov.bloom.message.model.service.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;}

    @GetMapping("/readmessage")
    public ModelAndView messageList(@RequestParam(defaultValue = "1")int pageNo, ModelAndView mv){
        int cmemberNo = 1;
        int totalCount = messageService.selectTotalCount();

        int limit = 8;

        int buttonAmount = 5;

        SelectCriteria selectCriteria = null;

        selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount );

        List<MessageDTO> messageDTOList = messageService.selectMessageList();

        mv.addObject("messageDTOList",messageDTOList);
        mv.addObject("selectCriteria",selectCriteria);
        mv.setViewName("content/message/Message");


        return mv;
    }


}
