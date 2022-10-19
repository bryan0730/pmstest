package com.springcloud.webclients.api.controller;

import com.springcloud.webclients.api.dto.AllOrganizationResponse;
import com.springcloud.webclients.api.dto.UserDto;
import com.springcloud.webclients.api.service.OrganizationService;
import com.springcloud.webclients.api.service.UserServcie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyUserController {

    private final UserServcie userServcie;
    private final OrganizationService organizationService;

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
    public String goSignUpPage(Model model){
        List<AllOrganizationResponse> organizations = organizationService.findAll();
        model.addAttribute("organizations", organizations);
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
