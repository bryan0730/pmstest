package com.springcloud.webclients.api.controller;

import com.springcloud.webclients.api.dto.AllOrganizationResponse;
import com.springcloud.webclients.api.dto.UserDto;
import com.springcloud.webclients.api.dto.UserSettingResponse;
import com.springcloud.webclients.api.service.OrganizationService;
import com.springcloud.webclients.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class UserSettingController {

    private final UserService userService;
    private final OrganizationService organizationService;

    @GetMapping
    public String goUserSettingForm(Model model){

        List<UserSettingResponse> userList = userService.findAllUser();
        model.addAttribute("userList", userList);

        return "user-setting";
    }
    /*
    redierct:/admin/settings
     */
    @ResponseBody
    @PostMapping("/join")
    public ResponseEntity<UserDto> signUp(UserDto userDto){

        UserDto resultDto = userService.signUp(userDto);
        log.info("result : {}", resultDto);
        System.out.println("MyUserController.signUp");
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/join")
    public String goSignUpPage(Model model){
        List<AllOrganizationResponse> organizations = organizationService.findAll();
        model.addAttribute("organizations", organizations);
        return "sign-up";
    }
}
