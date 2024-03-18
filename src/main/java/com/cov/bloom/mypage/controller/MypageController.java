package com.cov.bloom.mypage.controller;

import com.cov.bloom.auth.model.service.AuthService;
import com.cov.bloom.member.model.dto.LoginMemberDTO;
import com.cov.bloom.member.model.dto.MemberDTO;
import com.cov.bloom.member.model.service.MailService;

import com.cov.bloom.mypage.model.service.MypageService;
import com.cov.bloom.order.model.dto.*;
import jakarta.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mypage")
public class MypageController {



    private final AuthService authService;
    private final MypageService mypageService;
    private final PasswordEncoder passwordEncoder;

    private final MailService mailService;

    private final HttpSession session;







    public MypageController(AuthService authService, MypageService mypageService, PasswordEncoder passwordEncoder,MailService mailService,HttpSession session){
        this.authService = authService;
        this.mypageService=mypageService;
        this.passwordEncoder=passwordEncoder;
        this.session=session;
        this.mailService=mailService;
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

    @PostMapping("/updatePW")
    @ResponseBody
    public Map<String, Object> updatePW(@RequestParam("pw") String pw){


        Map<String, Object> Result = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberName= authentication.getName();

        String encpw =  passwordEncoder.encode(pw);

        System.out.println(encpw);


        int result =  mypageService.updatePW(memberName,encpw);

        if(result > 0){
            Result.put("status","success");
            Result.put("message","비밀번호 수정완료.");

        }else{
            Result.put("status","fail");
            Result.put("message","비밀번호 수정실패.");
        }
        return Result;
    }

    @PostMapping("/passCheck")
    public String passCheck(@RequestParam("pw") String pw){


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        LoginMemberDTO member = mypageService.findByUsername(email);


        System.out.println("로그인한 맴버 비밀번호 =" + member.getPassword());
        System.out.println("입력받은 비밀번호 = " + pw);

       if (passwordEncoder.matches(pw,member.getPassword())){
           System.out.println("비밀번호 일치");
           return"content/member/auth/unRegister2";
       }else {

           System.out.println("비밀번호 불일치");
          return "content/member/auth/unRegister1";

       }

    }

    @GetMapping("/email")
    public String email(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberName= authentication.getName();

        String authCode = createAuthCode();
        String email = memberName;

        session.setAttribute("authCode",authCode);


        mailService.sendMail(email,authCode);

        return "content/member/signin";
    }

    @PostMapping("/verify")
    @ResponseBody
    public Map<String, Object> verify(String code){

        Map<String, Object> result = new HashMap<>();
        System.out.println("전달된 코드는 "+ code + " 입니다.");
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

    @GetMapping("/unRegister1")
    public String unRegister1(){
        return "/content/member/auth/unRegister1";
    }

    @GetMapping("/delete")
    public String deleteMember(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberName= authentication.getName();
      int result =  mypageService.deleteMember(memberName);


        return "/main";
    }

    @GetMapping("/salesRegist")
    public String salesRegist(){
        return "/content/member/auth/salesRegistration";
    }

    @GetMapping("/getSales")
    public String getSales(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberName= authentication.getName();
        int result =  mypageService.getSales(memberName);

        System.out.println("결과는 " + result);

        return"content/mypage/MyPage";
    }

    public String createAuthCode() {

        return String.valueOf((int)(Math.random() * 900000) + 100000);
    }

    //주문내역
    @GetMapping("/orderlist")
    public String orderlist(Model model){

        int memberNo = 2; //의뢰인 회원번호
        List<MyOrder> orderlist = mypageService.findAllOrderList(memberNo);


        model.addAttribute("myorder", orderlist);

        System.out.println("주문내역");

        return "content/mypage/orderList";
    }

    //판매내역
    @GetMapping("/orderSalelist")
    public String orderSalelist(Model model){

        int memberNo = 1; //판매자 회원번호
        String portNo = memberNo + "_p";
        List<MyOrder> orderSalelist = mypageService.findAllOrderSaleList(portNo);


        model.addAttribute("myorder", orderSalelist);

        System.out.println("판매내역");

        return "content/mypage/orderSaleList";
    }


    //주문상세
    @GetMapping("/orderDetail")
    public String oderDetail(int orderNo, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        LoginMemberDTO member = mypageService.findByUsername(authentication.getName());
        System.out.println(member);
        //로그인 회원

        System.out.println(orderNo); //체크

        //주문 정보
        OrderDetailDTO orderDetail = mypageService.getOrderDetail(orderNo);

        model.addAttribute("orderDetail", orderDetail);

        //판매자 페이지로 갈때는 1
        model.addAttribute("memberNo", member);

        System.out.println(orderDetail);
        System.out.println(orderDetail.getGuideFile());
        System.out.println(orderDetail.getRequestFile());

        return "content/mypage/orderDetail";
    }

    //가이드 제출
    @PostMapping("/submitGuide")
    public String submitGuide(@RequestParam("orderNo") int orderNo,
                              @RequestParam("guide")MultipartFile multipartFile){

        System.out.println("체크");
        System.out.println(orderNo);
        System.out.println(multipartFile);

        OrderDTO order = new OrderDTO();

        order.setOrderNo(orderNo);
        order.setOrderFinalDt(new java.sql.Date(System.currentTimeMillis())); //완료시간
        order.setRequestStatus("D"); // done

        mypageService.submitGuide(order, multipartFile);

        return "redirect:/mypage/orderSalelist";
    }

    //의뢰인 구매확정
    @PostMapping("/purchaseConfirm")
    public String purchaseConfirm(Integer orderNo, String reqStatus){

        OrderDTO order =  new OrderDTO();

        order.setOrderNo(orderNo);
        order.setRequestStatus(reqStatus);
        order.setOrderFinal("Y");       //구매 확정 여부

        mypageService.purchaseConfirm(order);

        return "jsonView";
    }

    @PostMapping("/updateStatus")
    public String updateStatus(Integer orderNo, String reqStatus){

        OrderDTO order = new OrderDTO();

        order.setOrderNo(orderNo);
        order.setRequestStatus(reqStatus);

        mypageService.updateStatus(order);

        return "jsonView";
    }
}
