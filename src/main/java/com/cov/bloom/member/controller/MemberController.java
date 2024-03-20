package com.cov.bloom.member.controller;

import com.cov.bloom.member.model.dto.MemberDTO;
import com.cov.bloom.member.model.service.MailService;
import com.cov.bloom.member.model.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    private final HttpSession session;




    public MemberController(MemberService memberService,MailService mailService,HttpSession session,PasswordEncoder passwordEncoder){
        this.memberService=memberService;
        this.mailService=mailService;
        this.session = session;
        this.passwordEncoder = passwordEncoder;


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


    @GetMapping("/findID")
    public String findID(){
        return "content/member/findID";
    }

    private String createAuthCode() {

        return String.valueOf((int)(Math.random() * 900000) + 100000);
    }

    @PostMapping("/findID")
    public ModelAndView findID(ModelAndView mv,String name, String phone){




        String resultID = memberService.findID(name,phone);



        if(resultID == null){

            mv.addObject("message","일치하는 이메일이 없습니다.");
            mv.setViewName("/content/member/findID");
            return mv;

        }else{
            mv.addObject("message","이메일은  " + resultID + "  입니다.");
            mv.setViewName("/content/member/login");
            return mv;
        }



    }

    @GetMapping("/findPW1")
    public String findPW1(){
        return "content/member/findPW1";
    }

    @PostMapping("/findPWEmail")
    @ResponseBody
    public Map<String, Object> findPWEmail(@RequestParam("email") String email){

        session.setAttribute("findPWEmail",email);

        Map<String, Object> Result = new HashMap<>();

        int result =  memberService.duplicationEmail(email);

        if(result == 0){
           Result.put("status","fail");
            Result.put("message","가입된 이메일이 없습니다.");

        }else{
            Result.put("status","success");
            Result.put("message","가입된 이메일이 있습니다.");
        }

        return Result;
    }

    @PostMapping("/verifyPW1")
    @ResponseBody
    public ModelAndView verifyPW1(ModelAndView mv, String code){



        createAuthCode();
        String sessionAuthCode = (String) session.getAttribute("authCode");




        if(code.equals(sessionAuthCode)){
           mv.addObject("message","인증에 성공하였습니다!");
           mv.setViewName("content/member/findPW2");

        }else{
            mv.addObject("message","인증에 실패하였습니다.");
            mv.setViewName("content/member/findPW1");
        }


        return mv;


    }


    @PostMapping("/findPW2")
    @ResponseBody
    public ModelAndView findPW(ModelAndView mv,@RequestParam("pw") String pw){






        String encpw =  passwordEncoder.encode(pw);



        String memberName = (String)session.getAttribute("findPWEmail");


        int result =  memberService.findPW(memberName,encpw);

        if(result > 0){
          mv.setViewName("content/member/login");
          mv.addObject("message","비밀번호 수정 완료!");

        }else{
            mv.setViewName("content/member/findPW2");
            mv.addObject("message","비밀번호 수정 실패!");
        }
        return mv;
    }












}
