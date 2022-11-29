package com.forwiz.pms.web.user.controller;

import com.forwiz.pms.domain.user.dto.PmsUserDetails;
import com.forwiz.pms.domain.user.dto.UserInfoChangeForm;
import com.forwiz.pms.domain.user.entity.PmsUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
