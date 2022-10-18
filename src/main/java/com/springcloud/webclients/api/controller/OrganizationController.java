package com.springcloud.webclients.api.controller;

import com.springcloud.webclients.api.dto.AllOrganizationDto;
import com.springcloud.webclients.api.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/organization")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping
    public String goOrganizationPage(Model model){

        List<AllOrganizationDto> allOrganizations = organizationService.findAll();

        return "organization-setting";
    }
}
