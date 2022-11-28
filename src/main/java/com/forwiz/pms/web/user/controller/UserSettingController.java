package com.forwiz.pms.web.user.controller;

import com.forwiz.pms.domain.rank.dto.RankInfoResponse;
import com.forwiz.pms.domain.user.dto.UserDto;
import com.forwiz.pms.domain.user.dto.UserDuplicatedResponse;
import com.forwiz.pms.domain.user.dto.UserSettingFormResponse;
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

    @GetMapping
    public String goUserSettingForm(Model model){

        UserSettingFormResponse userFormResponse = userService.makeUserSettingFormData(1L);
        model.addAttribute("userFormList", userFormResponse);

        return "user-setting";
    }

    @ResponseBody
    @PostMapping("/rank-info")
    public List<RankInfoResponse> changeSelectBox(@RequestBody Long orgId){
        return userService.getUserSettingRankInfo(orgId);
    }

    @ResponseBody
    @PostMapping("/del")
    public String delUser(@RequestBody List<Map<String, Long>> mapList){

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
        return userService.idDuplicatedCheck(verification);
    }
}
