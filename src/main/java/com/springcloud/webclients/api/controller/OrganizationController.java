package com.springcloud.webclients.api.controller;

import com.springcloud.webclients.api.dto.AllOrganizationResponse;
import com.springcloud.webclients.api.dto.SaveOrganizationRequest;
import com.springcloud.webclients.api.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public String delOrganization(@RequestBody List<Map<String, Long>> mapList){

        int delCount = organizationService.delOrganization(mapList);
        String msg = delCount + "개 삭제완료 하였습니다.";

        return msg;
    }
}
