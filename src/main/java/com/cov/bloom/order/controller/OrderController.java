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
    public String request(Model model){

        model.addAttribute("portNo", "p");

        return "order/request";
    }

    @GetMapping("/successOrder")
    public String success(){
        return "order/successOrder";
    }



    @GetMapping("/failOrder")
    public String fail(){
        return "order/failOrder";
    }



    @GetMapping("/getOption")
    public String receiveOption(@RequestParam("optionValue") String optionNo,
                                @RequestParam("portNo") String portNo,
                                Model model) throws JsonProcessingException {

        Map<String, String> searchOption = new HashMap<>();
        searchOption.put("optionNo", optionNo);
        searchOption.put("portNo", portNo);

        OptionDTO option = orderService.getOption(searchOption);

        ObjectMapper mapper = new ObjectMapper();

        model.addAttribute("option", mapper.writeValueAsString(option));

        return "jsonView";
    }

}