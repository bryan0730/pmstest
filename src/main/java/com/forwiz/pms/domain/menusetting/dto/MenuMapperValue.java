package com.forwiz.pms.domain.menusetting.dto;

import lombok.Getter;

@Getter
public class MenuMapperValue {

    private String menuName;
    private String menuUrl;
    private String menuCode;

    public MenuMapperValue(MenuMapperType mapperType){
        menuName = mapperType.getMenuName();
        menuUrl = mapperType.getMenuUrl();
        menuCode = mapperType.getMenuCode();
    }
}
