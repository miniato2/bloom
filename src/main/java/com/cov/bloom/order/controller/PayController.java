package com.cov.bloom.order.controller;

import com.cov.bloom.order.model.dto.*;
import com.cov.bloom.order.model.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
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

    private final PayService payservice;

    @Autowired
    private ResourceLoader resourceLoader;

    private OptionDTO option;

    private OrderDTO order;


    public PayController(PayService payservice){
        this.payservice = payservice;
        this.option = new OptionDTO();
        this.order = new OrderDTO();
    }

    @PostMapping("/ready")
    public String kakaoPay(@ModelAttribute("order") OrderDTO order,
                           @ModelAttribute("option") OptionDTO option,
                           @RequestParam("file[]") List<MultipartFile> multipartFiles) throws IOException {


        //파일업로드
        fileUpload(multipartFiles);

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
        this.order.setOrderNo("a123456");


        System.out.println(this.option);
        System.out.println(this.order);

        ReadyResponse readyResponse = payservice.readyPay(this.order, this.option);

        System.out.println(readyResponse);

        String url = readyResponse.getNext_redirect_pc_url();

        return "redirect:"+url;
    }

    @GetMapping("/success")
    public String paySuccess(@RequestParam("pg_token") String pgToken, Model model){
        System.out.println("dddd");

        //결제요청
        ApproveResponse approveResponse = payservice.approvePay(pgToken, order);

        order.setOrderDt(approveResponse.getApproved_at());

        // 거래완료 주문db에 저장
        int result = payservice.registOrder(order);

        if(result>0){
            System.out.println("성공");
        }
        model.addAttribute("price", option.getOptionPrice());

        return "/order/successOrder";
    }

    @GetMapping("/fail")
    public String payFail(){
        return "redirect:";
    }

    @GetMapping("/cancel")
    public String payCancel(){
        System.out.println("2");

        return "";
    }

    public void fileUpload(List<MultipartFile> multipartFiles) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:static/img/multi");
        String filepath = null;

        if(!resource.exists()){
            String root = "src/main/resources/static/img/multi";

            File file = new File(root);
            file.mkdirs();

            filepath = file.getAbsolutePath();

        }else{
            filepath = resource.getFile().getAbsolutePath();
        }

        List<RequestFileDTO> files = new ArrayList<>();
        List<String> saveFiles = new ArrayList<>();
        try{
            for(MultipartFile file : multipartFiles){
                String originFileName = file.getOriginalFilename();
                String ext = originFileName.substring(originFileName.lastIndexOf("."));
                String savedFileName = UUID.randomUUID().toString().replace("-","") + ext;

                files.add(new RequestFileDTO(filepath, savedFileName, order.getOrderNo()));

                file.transferTo(new File(filepath + "/" + savedFileName));

                saveFiles.add("static/img/multi/" + savedFileName);

                System.out.println();

            }
        }catch (Exception e){
            for(RequestFileDTO file : files) {
                new File(filepath + "/" + file.getFileName()).delete();
            }
        }
    }
}
