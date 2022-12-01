package com.forwiz.pms.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forwiz.pms.domain.organization.service.OrganizationService;
import com.forwiz.pms.domain.rank.service.RankService;

import com.forwiz.pms.domain.user.service.UserService;
import com.forwiz.pms.web.message.controller.MessageController;
import com.forwiz.pms.web.organization.controller.OrganizationController;
import com.forwiz.pms.web.rank.RankController;
import com.forwiz.pms.web.user.controller.UserInfoChangeController;
import com.forwiz.pms.web.user.controller.UserSettingController;;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest({UserSettingController.class, UserInfoChangeController.class, OrganizationController.class, MessageController.class, RankController.class})
public class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @MockBean
    protected UserService userService;
    @MockBean
    protected OrganizationService organizationService;
    @MockBean
    protected MessageController messageController;
    @MockBean
    protected RankService rankService;

    @BeforeEach
    void createMember(){

    }
}
