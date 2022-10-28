package com.forwiz.pms.web.user.controller;

import com.forwiz.pms.domain.organization.dto.OrganizationListResponse;
import com.forwiz.pms.domain.user.dto.UserDto;
import com.forwiz.pms.domain.user.dto.UserSettingResponse;
import com.forwiz.pms.domain.organization.service.OrganizationService;
import com.forwiz.pms.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @ResponseBody
    @PostMapping("/del")
    public String delUser(@RequestBody List<Map<String, Long>> mapList){

        int delCount = userService.delUser(mapList);
        String msg = delCount + "개 삭제완료 하였습니다.";

        return msg;
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
        List<OrganizationListResponse> organizations = organizationService.selectOrganizationList();
        model.addAttribute("organizations", organizations);
        return "sign-up";
    }
}
