package com.forwiz.pms.web.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class PmsUserController {

    @GetMapping("/login-page")
    public String goLogin(){
        return "login";
    }

    @GetMapping("/pms")
    public String goPms(){
        return "pms";
    }

    @GetMapping("/err/denied-page")
    public String goDenied(){
        return "denied";
    }

    @GetMapping("/header")
    public String goHeader(){
        return "fragment/header";
    }

    @GetMapping("/pms/dashboard")
    public String goSide(){
        return "dashboard";
    }

    @GetMapping("/boot")
    public String goBoot(){
        return "fragment/layout-static";
    }

}
