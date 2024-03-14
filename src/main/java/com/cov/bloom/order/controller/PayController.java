package com.cov.bloom.order.controller;

import com.cov.bloom.order.model.dto.*;
import com.cov.bloom.order.model.service.PayService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;

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

    private List<RequestFileDTO> files;

    private OptionDTO option;

    private OrderDTO order;


    public PayController(PayService payservice){
        this.payservice = payservice;
        this.files = new ArrayList<>();
        this.option = new OptionDTO();
        this.order = new OrderDTO();
    }

    @PostMapping("/ready")
    public String kakaoPay(@ModelAttribute("order") OrderDTO order,
                           @ModelAttribute("option") OptionDTO option,
                           @RequestParam("file[]") List<MultipartFile> multipartFiles,
                           HttpSession session) throws IOException {

        this.option.setOptionNo(option.getOptionNo());
        this.option.setPortNo(option.getPortNo());
        this.option.setOptionDt(option.getOptionDt());
        this.option.setOptionFix(option.getOptionFix());
        this.option.setOptionPrice(option.getOptionPrice());
        this.option.setOptionInfo(option.getOptionInfo());

        this.order.setPortNo(order.getPortNo());
        this.order.setRequestCon(order.getRequestCon());
        this.order.setRequestStatus("W");
        this.order.setOrderFinal("N");
        this.order.setMemberNo(1);
        this.order.setOptionNo(this.option.getOptionNo());

        //db조회해서 주문번호 확인하는 로직
        int result = payservice.checkOrderNo();
        System.out.println(result);
        //현재 등록된 주문번호중 마지막 주문번호+1 = 현재 주문번호가된다.
        this.order.setOrderNo(result+1);

        fileUpload(multipartFiles); //파일 업로드
        session.setAttribute("files", files);

        session.setAttribute("multi", multipartFiles);

        //체크
        System.out.println(this.option);
        System.out.println(this.order);

        ReadyResponse readyResponse = payservice.readyPay(this.order, this.option);

        System.out.println(readyResponse);

        String url = readyResponse.getNext_redirect_pc_url();

        return "redirect:"+url;
    }

    @GetMapping("/success")
    public String paySuccess(@RequestParam("pg_token") String pgToken, Model model, HttpSession session){


        //세션 체크
        System.out.println(session.getAttribute("files").toString());

        System.out.println("이미지 세션 저장 확인");
        System.out.println(session.getAttribute("multi").toString());


        //결제요청
        ApproveResponse approveResponse = payservice.approvePay(pgToken, order);

        order.setOrderDt(approveResponse.getApproved_at());

        // 거래완료 주문db에 저장, + 사진
        payservice.registOrder(order, files);

        model.addAttribute("price", option.getOptionPrice());

        return "content/order/successOrder";
    }

    @GetMapping("/fail")
    public String payFail(){
        return "content/order/failOrder";
    }

    @GetMapping("/cancel")
    public String payCancel(){
        return "content/order/failOrder";
    }



    //파일 업로드
    public void fileUpload(List<MultipartFile> multipartFiles) throws IOException {

        String rootLocation = ROOT_LOCATION + IMAGE_DIR;

        String fileUploadDirectory = rootLocation + "/upload/request/original";

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
                files.add(new RequestFileDTO(fileUploadDirectory, savedFileName, order.getOrderNo()));
            }


        }catch (IllegalStateException | IOException e){
            // Exception 발생시 파일 삭제
            for(int i = 0; i < files.size(); i++){
                File deleteFile = new File(fileUploadDirectory + "/" + files.get(i).getFileName());

                boolean isDeleted = deleteFile.delete();
            }
        }
        System.out.println(files);
    }
}
