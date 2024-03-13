package com.cov.bloom.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public String login(){
        return "content/member/login";
    }
    @GetMapping("/success")
    public String success(){
        return "content/member/auth/main";
    }

    @GetMapping("/fail")
    public ModelAndView loginFail(ModelAndView mv, @RequestParam String message){
        mv.addObject("message", message);
        mv.setViewName("content/member/auth/fail");

        return mv;
    }
    @GetMapping("/mypage")
    public String myPage(){
        return "content/mypage/MyPage";
    }


}
