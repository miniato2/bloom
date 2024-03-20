package com.cov.bloom.order.controller;

import com.cov.bloom.order.model.dto.OptionDTO;
import com.cov.bloom.order.model.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("/request")
    public String request(Model model, String portNo){

        model.addAttribute("portNo", portNo);

        return "content/order/request";
    }

    @GetMapping("/successOrder")
    public String success(){
        return "content/order/successOrder";
    }

    @GetMapping("/failOrder")
    public String fail(){
        return "content/order/failOrder";
    }


    @GetMapping("/getOption")
    public String receiveOption(@RequestParam("optionNo") String optionNo,
                                Model model) throws JsonProcessingException {

        OptionDTO option = orderService.getOption(optionNo);

        ObjectMapper mapper = new ObjectMapper();

        model.addAttribute("option", mapper.writeValueAsString(option));

        return "jsonView";
    }

}