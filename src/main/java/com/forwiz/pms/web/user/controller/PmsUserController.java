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



    @GetMapping("/err/denied-page")
    public String goDenied(){
        return "error/denied";
    }

    @GetMapping({"/","/pms"})
    public String goMain(){
        return "redirect:/pms/board/notice";
    }

    @GetMapping("/admin")
    public String goAdmin(){
        return "redirect:/admin/organization";
    }

}
