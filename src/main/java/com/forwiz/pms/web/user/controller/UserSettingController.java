package com.forwiz.pms.web.user.controller;

import com.forwiz.pms.domain.organization.dto.OrganizationListResponse;
import com.forwiz.pms.domain.organization.exception.DeleteListEmptyException;
import com.forwiz.pms.domain.user.dto.UserDto;
import com.forwiz.pms.domain.user.dto.UserDuplicatedResponse;
import com.forwiz.pms.domain.user.dto.UserSettingResponse;
import com.forwiz.pms.domain.organization.service.OrganizationService;
import com.forwiz.pms.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        List<OrganizationListResponse> organizations = organizationService.selectOrganizationList();
        model.addAttribute("organizations", organizations);
        return "user-setting";
    }

    @ResponseBody
    @PostMapping("/del")
    public String delUser(@RequestBody List<Map<String, Long>> mapList){

        if(mapList.size()==0 || mapList.get(0).get("id")==1){
            throw new DeleteListEmptyException("삭제할 데이터가 없습니다.");
        }

        int delCount = userService.delUser(mapList);

        return delCount + "개 삭제완료 하였습니다.";
    }

    @PostMapping("/join")
    public String signUp(@Valid UserDto userDto){

        userService.signUp(userDto);

        return "redirect:/admin/user";
    }

    @ResponseBody
    @PostMapping("/verify")
    public UserDuplicatedResponse idDuplicatedCheck(@RequestBody String verification){

        log.info("verify id : {}", verification);
        return userService.idDuplicatedCheck(verification);
    }
}
