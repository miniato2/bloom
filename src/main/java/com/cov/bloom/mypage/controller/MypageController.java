package com.cov.bloom.mypage.controller;

import com.cov.bloom.auth.model.service.AuthService;
import com.cov.bloom.common.paging.Pagenation;
import com.cov.bloom.common.paging.SelectCriteria;
import com.cov.bloom.member.model.dto.LoginMemberDTO;
import com.cov.bloom.member.model.service.MailService;

import com.cov.bloom.mypage.model.service.MypageService;
import com.cov.bloom.order.model.dto.*;
import jakarta.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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







    public MypageController(AuthService authService, MypageService mypageService, PasswordEncoder passwordEncoder,MailService mailService, HttpSession session){
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


        System.out.println();

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
    @Transactional
    public String deleteMember(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberName= authentication.getName();
      int result =  mypageService.deleteMember(memberName);


        return "redirect:/auth/logout";
    }

    @GetMapping("/salesRegist")
    @Transactional
    public String salesRegist(){
        return "/content/member/auth/salesRegistration";
    }

    @GetMapping("/getSales")
    @Transactional
    public String getSales(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberName= authentication.getName();
        int result =  mypageService.getSales(memberName);

        System.out.println("결과는 " + result);

        return"redirect:/mypage/main";
    }

    public String createAuthCode() {

        return String.valueOf((int)(Math.random() * 900000) + 100000);
    }

    //주문내역
    @GetMapping("/orderlist")
    public String orderlist(@RequestParam(value = "currentPage", defaultValue = "1") int pageNo
                            ,Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginMemberDTO member = mypageService.findByUsername(authentication.getName());
        int memberNo = member.getNo(); //의뢰인 회원번호 member.getNo();

        //전체 목록 사이즈
        int totalCount = mypageService.selectTotalOrder(memberNo);

        //한페이지에 보여줄 내역 수
        int limit = 10;

        //한페이지에 보여줄 버튼 수
        int buttonAmount = 3;

        //페이징 처리에 관한 정보를 담고 있는 인스턴스
        String searchValue = authentication.getName();
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount,"",searchValue);

        List<MyOrder> orderList = mypageService.selectOrderList(selectCriteria);

        model.addAttribute("orderList", orderList);
        model.addAttribute("selectCriteria", selectCriteria);

        return "content/mypage/orderList";
    }

    //판매내역
    @GetMapping("/orderSalelist")
    public String orderSalelist(@RequestParam(value = "currentPage", defaultValue = "1") int pageNo
                                ,Model model){
        //현재 로그인회원 번호 조회
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginMemberDTO member = mypageService.findByUsername(authentication.getName());

        int memberNo = member.getNo(); //판매자 회원번호 member.getNo();
        String portNo = memberNo + "_p";

        //전체 목록 사이즈
        int totalCount = mypageService.selectTotalSale(portNo);

        //한페이지에 보여줄 내역 수
        int limit = 10;

        //한페이지에 보여줄 버튼 수
        int buttonAmount = 3;

        //페이징 처리에 관한 정보를 담고 있는 인스턴스
        String searchValue = portNo;
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount,"",searchValue);

        List<MyOrder> saleList = mypageService.selectSaleList(selectCriteria);

        model.addAttribute("saleList", saleList);
        model.addAttribute("selectCriteria", selectCriteria);

        return "content/mypage/orderSaleList";
    }


    //주문상세
    @GetMapping("/orderDetail")
    public String oderDetail(int orderNo, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        LoginMemberDTO member = mypageService.findByUsername(authentication.getName());
        //로그인 회원

        //주문 정보
        OrderDetailDTO orderDetail = mypageService.getOrderDetail(orderNo);

        model.addAttribute("orderDetail", orderDetail);

        //판매자 페이지로 갈때는 1
        model.addAttribute("memberNo", member.getNo());

        System.out.println(orderDetail);
        System.out.println(orderDetail.getGuideFile());
        System.out.println(orderDetail.getRequestFile());

        return "content/mypage/orderDetail";
    }

    //가이드 제출
    @PostMapping("/submitGuide")
    public String submitGuide(@RequestParam("orderNo") int orderNo,
                              @RequestParam("guide")MultipartFile multipartFile){

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
