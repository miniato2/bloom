package com.cov.bloom.message.controller;

import com.cov.bloom.common.exception.MessageRegistException;
import com.cov.bloom.common.paging.Pagenation;
import com.cov.bloom.common.paging.SelectCriteria;
import com.cov.bloom.member.model.dto.MemberDTO;
import com.cov.bloom.message.model.dto.MessageDTO;
import com.cov.bloom.message.model.service.MessageService;
import com.cov.bloom.portfolio.model.dto.PortfolioDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    public String selectMessageDetail(@RequestParam long no, Model model){
        MessageDTO messageDetail = messageService.selectMessagDetail(no);

        model.addAttribute("board", messageDetail);

        return null;
    }


    @GetMapping("/messagebox")
    public ModelAndView messageBox(@RequestParam(defaultValue = "1") int pageNo, ModelAndView mv) {
        int cmemberNo = 1; // 로그인된 회원의 회원 번호
        int totalReceivedCount = messageService.selectTotalReceivedCount(cmemberNo); // 받은 쪽지함의 총 갯수
        int totalSentCount = messageService.selectTotalSentCount(cmemberNo); // 보낸 쪽지함의 총 갯수

        int limit = 8; // 페이지당 보여줄 쪽지 갯수
        int buttonAmount = 5; // 페이지 버튼의 갯수

        // 받은 쪽지함 페이지네이션
        SelectCriteria receivedCriteria = Pagenation.getSelectCriteria(pageNo, totalReceivedCount, limit, buttonAmount);
        List<MessageDTO> receivedMessageDTOList = messageService.selectReceivedMessageList(cmemberNo, receivedCriteria);

        // 보낸 쪽지함 페이지네이션
        SelectCriteria sentCriteria = Pagenation.getSelectCriteria(pageNo, totalSentCount, limit, buttonAmount);
        List<MessageDTO> sentMessageDTOList = messageService.selectSentMessageList(cmemberNo, sentCriteria);

        mv.addObject("receivedMessageDTOList", receivedMessageDTOList);
        mv.addObject("sentMessageDTOList", sentMessageDTOList);
        mv.addObject("receivedSelectCriteria", receivedCriteria);
        mv.addObject("sentSelectCriteria", sentCriteria);
        mv.setViewName("content/message/MessageBox");

        return mv;
    }


    @GetMapping("/send")
    public String sendMessage (@RequestParam("memberNo") int memberNo
                                                            , @RequestParam ("nickname") String nickname
                                                            , Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String memberEmail = authentication.getName();
        MemberDTO mymember = messageService.findMemberId(memberEmail);
        int memNo = mymember.getNo();
        String memNick =mymember.getNickname();
        System.out.println(nickname);

//        String rememNick = nickname.getMemberNickname().toString();
        model.addAttribute("rememNick",nickname);
        model.addAttribute("memberNo",memberNo);
        model.addAttribute("senderMember",memNo);
        model.addAttribute("senderNick",memNick);

        return "content/message/sendMessage";

    }

    @PostMapping("/send")
    public String send (@RequestParam int senderMember,
                        @RequestParam String senderNick,
                        @RequestParam int rememberNo,
                        @RequestParam String recipient,
                        @RequestParam MessageDTO messageDTO) throws MessageRegistException {

        String sendEmail =messageService.findMemberEmail(senderMember);
        String reciEmail =messageService.findMemberEmail(rememberNo);


       messageDTO.setSenderMemberEmail(sendEmail);
       messageDTO.setRecipientMemberEmail(reciEmail);

       messageService.registMessage(messageDTO);

        return "content/message/Message";

    }





}
