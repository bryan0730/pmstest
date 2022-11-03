package com.forwiz.pms.web.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class PmsUserController {

    @GetMapping("/login-page")
    public String goLogin(){
        return "login";
    }

    @GetMapping("/pms/board")
    public String goPms(){
        return "pms";
    }

    @GetMapping("/err/denied-page")
    public String goDenied(){
        return "denied";
    }

    @GetMapping("/boot")
    public String goBoot(){
        return "fragment/layout-static";
    }

}
