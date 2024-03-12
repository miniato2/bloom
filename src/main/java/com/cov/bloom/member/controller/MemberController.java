package com.cov.bloom.member.controller;

import com.cov.bloom.member.model.dto.MemberDTO;
import com.cov.bloom.member.model.service.MailService;
import com.cov.bloom.member.model.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/member")
@EnableAsync
public class MemberController {

    private final MemberService memberService;


    private final MailService mailService;

    private final HttpSession session;




    public MemberController(MemberService memberService,MailService mailService,HttpSession session){
        this.memberService=memberService;
        this.mailService=mailService;
        this.session = session;


    }

    @GetMapping("/signin")
    public String signin(){

        return "content/member/signin";
    }

    @PostMapping("/signin")
    public String signin(MemberDTO newMember){

        memberService.signinMember(newMember);

        return "content/member/login";
    }
    @PostMapping("/duplicationEmail")
    @ResponseBody
    public Map<String, Object> duplication(@RequestParam("email") String email){
        System.out.println(email);

        Map<String, Object> duplResult = new HashMap<>();

        int result =  memberService.duplicationEmail(email);

        if(result == 0){
            duplResult.put("status","success");
            duplResult.put("message","중복없음.");

        }else{
            duplResult.put("status","duplication");
            duplResult.put("message","중복된 이메일입니다");
        }
        return duplResult;
    }

    @PostMapping("/email")
    public String email(String email){

        String authCode = createAuthCode();

        session.setAttribute("authCode",authCode);


        mailService.sendMail(email,authCode);

        return "content/member/signin";
    }

    
    @PostMapping("/verify")
    @ResponseBody
    public Map<String, Object> verify(String code){

        Map<String, Object> result = new HashMap<>();
        System.out.println("전달된 코드는 "+code + " 입니다.");
        String sessionAuthCode = (String) session.getAttribute("authCode");



        if(code.equals(sessionAuthCode)){
            result.put("status", "success");
            result.put("message","인증성공");


        }else{
            result.put("status", "failure");
            result.put("message","인증실패");

        }


        return result;


    }

    private String createAuthCode() {

        return String.valueOf((int)(Math.random() * 900000) + 100000);
    }






}
