package com.forwiz.pms.domain.menusetting.dto;

import com.forwiz.pms.domain.menusetting.dto.MenuMapperType;

public enum MenuUrl implements MenuMapperType {

    NOTICE_BOARD("공지 게시판","/pms/board/notice"),
    BUSINESS_BOARD("업무 게시판","/pms/board/business"),
    MESSAGE("메시지","/pms/message"),
    USER_SETTING("사용자 관리","/admin/user"),
    ORG_SETTING("조직 관리","/admin/organization"),
    MENU_SETTING("메뉴 관리","/admin/menu")
    ;

    private final String menuName;
    private final String menuUrl;

    MenuUrl(String menuName, String menuUrl){
        this.menuName = menuName;
        this.menuUrl = menuUrl;
    }

    @Override
    public String getMenuName() {
        return menuName;
    }

    @Override
    public String getMenuUrl() {
        return menuUrl;
    }

    @Override
    public String getMenuCode() {
        return name();
    }
}
