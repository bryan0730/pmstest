package com.springcloud.webclients.api.controller;

import com.springcloud.webclients.api.dto.OrganizationListResponse;
import com.springcloud.webclients.api.dto.SaveOrganizationRequest;
import com.springcloud.webclients.api.service.OrganizationService;
import com.springcloud.webclients.api.util.DuplicateConfirmation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        List<OrganizationListResponse> allOrganizations = organizationService.selectOrganizationList();

        model.addAttribute("orgList", allOrganizations);

        return "organization-setting";
    }

    @PostMapping("/save")
    public String saveOrganization(SaveOrganizationRequest saveOrganizationRequest, RedirectAttributes re){

        DuplicateConfirmation confirmation = organizationService.saveOrganization(saveOrganizationRequest);
        log.info("SaveOrganization Duplicate Result Msg : {}", confirmation.getComment());
        re.addFlashAttribute("resultMsg", confirmation.getComment());

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
