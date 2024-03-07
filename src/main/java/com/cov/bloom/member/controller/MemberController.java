package com.cov.bloom.member.controller;

import com.cov.bloom.member.model.dto.MemberDTO;
import com.cov.bloom.member.model.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService=memberService;

    }

    @GetMapping("/signin")
    public String signin(){

        return "content/member/signin";
    }

    @PostMapping("/signin")
    public String signin(MemberDTO newMember){

        memberService.signinMember(newMember);

        return "content/member/success";
    }




}
