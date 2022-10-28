package com.forwiz.pms.web.menusetting.controller;

import com.forwiz.pms.domain.menusetting.dto.MenuMapperValue;
import com.forwiz.pms.domain.menusetting.service.MenuMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/menu")
@RequiredArgsConstructor
public class MenuSettingController {

    private final MenuMapper menuMapper;

    @GetMapping
    public String menuUrl(Model model){
        List<MenuMapperValue> menuUrl = menuMapper.get("MenuUrl");
        model.addAttribute("url", menuUrl);

        return "menu-setting";
    }
}
