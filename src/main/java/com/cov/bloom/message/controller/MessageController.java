package com.cov.bloom.message.controller;

import com.cov.bloom.common.exception.MessageRegistException;
import com.cov.bloom.common.paging.Pagenation;
import com.cov.bloom.common.paging.SelectCriteria;
import com.cov.bloom.member.model.dto.MemberDTO;
import com.cov.bloom.message.model.dto.MessageDTO;
import com.cov.bloom.message.model.service.MessageService;
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
    private SelectCriteria sentCriteria;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;}

    @GetMapping("/sendermessage") //보낸 편지함
    public ModelAndView senderMessageList(@RequestParam(defaultValue = "1")int pageNo, ModelAndView mv){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String senderMemberEmail = authentication.getName();

        int totalCount = messageService.selectTotalCount();

        int limit = 8;

        int buttonAmount = 5;

        SelectCriteria selectCriteria = null;

        selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount );

        List<MessageDTO> senderMessageDTOList = messageService.selectMessageList(senderMemberEmail);

        mv.addObject("messageDTOList",senderMessageDTOList);
        mv.addObject("selectCriteria",selectCriteria);
        mv.setViewName("content/message/Message");


        return mv;
    }

    @GetMapping("/readmessage") //받은쪽지함
    public ModelAndView readMessageList(@RequestParam(defaultValue = "1")int pageNo,ModelAndView mv){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String readMemberEmail = authentication.getName();

        int totalCount = messageService.selectTotalCount();

        int limit = 8;

        int buttonAmount = 5;

        SelectCriteria selectCriteria = null;

        selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount );

        List<MessageDTO> readMessageDTOList = messageService.receiveMemberEmail(readMemberEmail);

        mv.addObject("messageDTOList",readMessageDTOList);
        mv.addObject("selectCriteria",selectCriteria);
        mv.setViewName("content/message/readMessage");

        System.out.println("controller 나감 ");

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
    public String send (@RequestParam("memberNo") int senderMember, //보낸사람 회원번호
                        @RequestParam("senderNick") String senderNick,    //보낸사람닉네임
                        @RequestParam("rememberNo") int rememberNo,   //받는사람 회원번호
                        @RequestParam("recipient") String recipient,     //받는사람닉네임
                        @ModelAttribute MessageDTO messageDTO) throws MessageRegistException {

        String sendEmail =messageService.findMemberEmail(senderMember); //보낸사람 이메일
        String reciEmail =messageService.findMemberEmail(rememberNo);   //받는사람 이메일

        System.out.println("asdfasd");
       messageDTO.setSenderMemberEmail(sendEmail);
       messageDTO.setRecipientMemberEmail(reciEmail);

       messageService.registMessage(messageDTO);

        return "content/message/Message";

    }

    @GetMapping("/sentbox")
    public ModelAndView sentBox(@RequestParam(defaultValue = "1") int pageNo, ModelAndView mv) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int currentUserEmail = selectMessageDetail(authentication);

        List<MessageDTO> sentMessageDTOList = messageService.selectSentMessageList(currentUserEmail, sentCriteria);

        mv.addObject("sentMessageDTOList", sentMessageDTOList);
        mv.setViewName("content/message/SentBox");

        return mv;
    }

    private int selectMessageDetail(Authentication authentication) {
        return Integer.parseInt(null);
    }


}
