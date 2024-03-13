package com.cov.bloom.mypage.controller;

import com.cov.bloom.auth.model.service.AuthService;
import com.cov.bloom.member.model.dto.LoginMemberDTO;
import com.cov.bloom.member.model.service.MemberService;
import com.cov.bloom.mypage.model.service.MypageService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/mypage")
public class MypageController {



    private final AuthService authService;
    private final MypageService mypageService;

    public MypageController(AuthService authService, MypageService mypageService){
        this.authService = authService;
        this.mypageService=mypageService;
    }



    @GetMapping("/main")
    public ModelAndView myPage(ModelAndView mv){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberName= authentication.getName();
        System.out.println(memberName);
        LoginMemberDTO member = authService.getMember(memberName);
        mv.addObject("member",member);
        mv.setViewName("content/mypage/MyPage");

        return mv;
    }

    @PostMapping("/updateNickname")
    @ResponseBody
    public Map<String, Object> updateNickname(@RequestParam("nickname") String nickname){


        Map<String, Object> updateNicknameResult = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberName= authentication.getName();

        int result =  mypageService.updateNickname(memberName,nickname);

        if(result > 0){
            updateNicknameResult.put("status","success");
            updateNicknameResult.put("message","닉네임 수정완료.");

        }else{
            updateNicknameResult.put("status","fail");
            updateNicknameResult.put("message","닉네임 수정실패.");
        }
        return updateNicknameResult;
    }

    @PostMapping("/updatePhone")
    @ResponseBody
    public Map<String, Object> updatePhone(@RequestParam("phone") String phone){


        Map<String, Object> Result = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberName= authentication.getName();

        int result =  mypageService.updatePhone(memberName,phone);

        if(result > 0){
            Result.put("status","success");
            Result.put("message","전화번호 수정완료.");

        }else{
            Result.put("status","fail");
            Result.put("message","전화번호 수정실패.");
        }
        return Result;
    }



}
