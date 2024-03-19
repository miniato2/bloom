package com.cov.bloom.order.controller;

import com.cov.bloom.member.model.dto.LoginMemberDTO;
import com.cov.bloom.mypage.model.service.MypageService;
import com.cov.bloom.order.model.dto.*;
import com.cov.bloom.order.model.service.PayService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Value("${image.image-dir}")
    private String IMAGE_DIR;

    @Value("${spring.servlet.multipart.location}")
    private String ROOT_LOCATION;

    private final PayService payservice;
    private final MypageService mypageService;

    private OptionDTO option;

    private OrderDTO order;

    public PayController(PayService payservice, MypageService mypageService){
        this.payservice = payservice;
        this.mypageService = mypageService;
        this.option = new OptionDTO();
        this.order = new OrderDTO();
    }

    @PostMapping("/ready")
    public String kakaoPay(@ModelAttribute("order") OrderDTO order,
                           @ModelAttribute("option") OptionDTO option,
                           @RequestParam("file[]") List<MultipartFile> multipartFiles,
                           HttpSession session) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        LoginMemberDTO member = mypageService.findByUsername(authentication.getName());

        this.option.setOptionNo(option.getPortNo()+"_op"+option.getOptionNo());
        this.option.setPortNo(option.getPortNo());
        this.option.setOptionDt(option.getOptionDt());
        this.option.setOptionFix(option.getOptionFix());
        this.option.setOptionPrice(option.getOptionPrice());
        this.option.setOptionInfo(option.getOptionInfo());

        this.order.setPortNo(order.getPortNo());
        this.order.setRequestCon(order.getRequestCon());
        this.order.setRequestStatus("W");
        this.order.setOrderFinal("N");
        this.order.setMemberNo(member.getNo());                              //의뢰인 회원번호
        this.order.setOptionNo(this.option.getOptionNo());

        //db조회해서 주문번호 확인하는 로직
        int result = payservice.checkOrderNo();
        //현재 등록된 주문번호중 마지막 주문번호+1 = 현재 주문번호가된다.
        this.order.setOrderNo(result+1);

        List<RequestFileDTO> files = fileUpload(multipartFiles); //주문번호 세팅하고 파일 업로드해야함

        session.setAttribute("files", files); //파일 업로드 후 dto list 세션에 저장

        ReadyResponse readyResponse = payservice.readyPay(this.order, this.option);

        this.order.setTid(readyResponse.getTid());

        String url = readyResponse.getNext_redirect_pc_url();

        return "redirect:"+url;
    }

    @GetMapping("/success")
    public String paySuccess(@RequestParam("pg_token") String pgToken, Model model, HttpSession session) throws IOException {

        //결제요청
        ApproveResponse approveResponse = payservice.approvePay(pgToken, order); //결제요청

        order.setOrderDt(approveResponse.getApproved_at()); //주문일시 세팅

        List<RequestFileDTO> files = (List<RequestFileDTO>) session.getAttribute("files"); //세션에 reqFile 형변환

        // 거래완료 주문db에 저장, + 사진
        payservice.registOrder(order, files);

        model.addAttribute("price", option.getOptionPrice());

        return "content/order/successOrder";
    }

    @GetMapping("/fail") //결제 실패
    public String payFail(HttpSession session, Model model){
        List<RequestFileDTO> files = (List<RequestFileDTO>) session.getAttribute("files");

        int cnt = 0;

        for(int i = 0; i < files.size();i++){
            File deleteFile = new File(files.get(i).getFilePath() + "/" + files.get(i).getFileName());
            boolean isDeleted = deleteFile.delete();
            if(isDeleted){
                cnt++;
            }
        }
        if(cnt == files.size()){
            System.out.println("삭제성공");
        }else{
            System.out.println("삭제실패");
        }

        model.addAttribute("price", option.getOptionPrice());

        return "content/order/failOrder";
    }

    @GetMapping("/cancel") //결제 중도 취소
    public String payCancel(HttpSession session){
        List<RequestFileDTO> files = (List<RequestFileDTO>) session.getAttribute("files");

        int cnt = 0;

        for(int i = 0; i < files.size();i++){
            File deleteFile = new File(files.get(i).getFilePath() + "/" + files.get(i).getFileName());
            boolean isDeleted = deleteFile.delete();
            if(isDeleted){
                cnt++;
            }
        }
        if(cnt == files.size()){
            System.out.println("삭제성공");
        }else{
            System.out.println("삭제실패");
        }

        return "content/order/failOrder";
    }


    @PostMapping("/cancelPay") //결제취소
    public String cancelPay(Integer orderNo, String reqStatus){

        OrderDTO order = new OrderDTO();

        order.setOrderNo(orderNo);
        order.setRequestStatus(reqStatus);

        payservice.cancelPay(order);

        return "jsonView";
    }

    //파일 업로드
    public List<RequestFileDTO> fileUpload(List<MultipartFile> multipartFiles) throws IOException {

        String rootLocation = ROOT_LOCATION + IMAGE_DIR;

        String fileUploadDirectory = rootLocation + "/upload/original";

        List<RequestFileDTO> files = new ArrayList<>();

        File directory = new File(fileUploadDirectory);

        if(!directory.exists()){
            directory.mkdirs();
        }
        try{
            for(MultipartFile multipartFile : multipartFiles){

                String originFileName = multipartFile.getOriginalFilename();
                String ext = originFileName.substring(originFileName.lastIndexOf("."));
                String savedFileName = UUID.randomUUID().toString().replace("-","") + ext;

                //저장
                multipartFile.transferTo(new File(fileUploadDirectory + "/" + savedFileName));

                //저장한 파일 dto 리스트에 담기
                files.add(new RequestFileDTO(fileUploadDirectory, savedFileName, this.order.getOrderNo()));
            }
        }catch (IllegalStateException | IOException e){
            e.printStackTrace();

            int cnt = 0;
            // Exception 발생시 파일 삭제
            for(int i = 0; i < files.size();i++){
                File deleteFile = new File(fileUploadDirectory + "/" + files.get(i).getFileName());
                boolean isDeleted = deleteFile.delete();
                if(isDeleted){
                    cnt++;
                }
            }
            if(cnt == files.size()){
                System.out.println("삭제성공");
            }else{
                System.out.println("삭제실패");
            }
        }
        return files;
    }
}
