package com.forwiz.pms.web.user.controller;

import com.forwiz.pms.domain.user.dto.PmsUserDetails;
import com.forwiz.pms.domain.user.dto.UserInfoChangeForm;
import com.forwiz.pms.domain.user.entity.PmsUser;
import com.forwiz.pms.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/pms/user")
@RequiredArgsConstructor
public class UserInfoChangeController {

    private final UserService userService;

    @GetMapping
    public String goUserInfo(@AuthenticationPrincipal PmsUserDetails user, Model model){

        PmsUser pmsUser = user.getPmsUser();
        model.addAttribute("userInfo"
                , new UserInfoChangeForm(pmsUser.getUserPhoneNumber(), pmsUser.getUserPw()));

        return "user-info-change";
    }

    @PostMapping
    public String changeUserInfo(UserInfoChangeForm userInfoChangeForm){

        userService.changeUserInfo(userInfoChangeForm);

        return "redirect:/pms/user";
    }
}
