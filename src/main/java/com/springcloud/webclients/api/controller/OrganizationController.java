package com.springcloud.webclients.api.controller;

import com.springcloud.webclients.api.dto.AllOrganizationResponse;
import com.springcloud.webclients.api.dto.DelOrganizationRequest;
import com.springcloud.webclients.api.dto.SaveOrganizationRequest;
import com.springcloud.webclients.api.service.OrganizationService;
import com.springcloud.webclients.api.util.OrgCodeCreater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/organization")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping
    public String goOrganizationForm(Model model){

        List<AllOrganizationResponse> allOrganizations = organizationService.findAll();

        model.addAttribute("orgList", allOrganizations);

        return "organization-setting";
    }

    @PostMapping("/save")
    public String saveOrganization(SaveOrganizationRequest saveOrganizationRequest){

        Long savedId = organizationService.saveOrganization(saveOrganizationRequest);
        log.info("save organization id = {}", savedId);

        return "redirect:/admin/organization";
    }

    @ResponseBody
    @PostMapping("/del")
    public String delOrganization(List<DelOrganizationRequest> requestDto){

//        for(Long id :requestDto.getOrgId()){
//            log.info("id : {}", id);
//        }
//
//        for(Boolean ch : requestDto.getIsChecked()){
//            log.info("ch : {}", ch);
//        }
        for(DelOrganizationRequest dto : requestDto){
            log.info("id : {}", dto.getOrgId());
        }
        return "---------ok-------------";
    }

}
