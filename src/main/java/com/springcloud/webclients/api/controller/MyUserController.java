package com.springcloud.webclients.api.controller;

import com.springcloud.webclients.api.dto.UserDto;
import com.springcloud.webclients.api.service.UserServcie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyUserController {

    private final UserServcie userServcie;

    /*
    redierct:/admin/settings
     */
    @ResponseBody
    @PostMapping("/sign-up")
    public ResponseEntity<UserDto> signUp(UserDto userDto){

        UserDto resultDto = userServcie.signUp(userDto);
        log.info("result : {}", resultDto);
        System.out.println("MyUserController.signUp");
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/go-sign")
    public String goSignUpPage(){
        return "sign-up";
    }

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
