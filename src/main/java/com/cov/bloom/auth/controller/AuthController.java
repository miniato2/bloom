package com.cov.bloom.auth.controller;

import com.cov.bloom.auth.model.service.AuthService;
import com.cov.bloom.member.model.dto.LoginMemberDTO;
import com.cov.bloom.member.model.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

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
    public ModelAndView loginFail(ModelAndView mv, @RequestParam("message") String message){




        mv.addObject("message", message);

        mv.setViewName("content/member/auth/fail");


        return mv;
    }




}
