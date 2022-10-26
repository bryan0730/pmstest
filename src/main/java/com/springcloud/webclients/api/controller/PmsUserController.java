package com.springcloud.webclients.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class PmsUserController {

    @GetMapping("/login-page")
    public String goLogin(){
        return "login2";
    }

    @GetMapping("/pms")
    public String goPms(){
        return "pms";
    }

    @GetMapping("/err/denied-page")
    public String goDenied(){
        return "denied";
    }

    @ResponseBody
    @GetMapping("/no")
    public String no(){
        return "no page";
    }

}
