package com.cov.bloom;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {



    @GetMapping(value={"/","/main"})
    public String main(){
        return "content/member/login";
    }




}
