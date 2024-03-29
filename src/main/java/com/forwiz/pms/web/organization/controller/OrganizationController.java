package com.forwiz.pms.web.organization.controller;

import com.forwiz.pms.domain.organization.dto.OrganizationListResponse;
import com.forwiz.pms.domain.organization.dto.SaveOrganizationRequest;
import com.forwiz.pms.domain.organization.exception.DeleteListEmptyException;
import com.forwiz.pms.domain.organization.service.OrganizationService;
import com.forwiz.pms.domain.organization.service.DuplicateConfirmation;
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
        re.addFlashAttribute("resultMsg", confirmation.getComment());

        return "redirect:/admin/organization";
    }

    @ResponseBody
    @PostMapping("/del")
    public String delOrganization(@RequestBody List<Map<String, Long>> mapList){

        if(mapList.size()==0){
            throw new DeleteListEmptyException("삭제할 데이터가 없습니다.");
        }

        int delCount = organizationService.delOrganization(mapList);

        return delCount + "개 삭제완료 하였습니다.";
    }
}
