package com.forwiz.pms.web.user.controller;

import com.forwiz.pms.domain.organization.dto.OrganizationListResponse;
import com.forwiz.pms.domain.organization.exception.DeleteListEmptyException;
import com.forwiz.pms.domain.rank.dto.RankInfoResponse;
import com.forwiz.pms.domain.user.dto.UserDto;
import com.forwiz.pms.domain.user.dto.UserDuplicatedResponse;
import com.forwiz.pms.domain.user.dto.UserInfoResponse;
import com.forwiz.pms.domain.organization.service.OrganizationService;
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
        //사용자 등록 모달에서 필요한 데이터 : 조직리스트/조직별 직급리스트
        //사용자 조회 폼에서 필요한 데이터 : 사용자리스트
        //리스트 3개 사용
        UserSettingFormResponse userFormResponse = userService.makeUserSettingFormData(1L);
        model.addAttribute("userFormList", userFormResponse);

        return "user-setting";
    }

    //유저등록 모달에서 select box 바뀔 때 마다 비동기적으로 가져올 컨트롤러 메소드 필요
    //아니다 그냥 사용자 등록버튼을 누르면 다시 서버 요청하게 할까?
    @ResponseBody
    @PostMapping("/rank-info")
    public List<RankInfoResponse> changeSelectBox(@RequestBody Long orgId){
        log.info("User setting form orgId : {}", orgId);
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

        log.info("rankId : {}", userDto.getRankId());
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
