package com.forwiz.pms.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forwiz.pms.domain.message.service.MessageFileStore;
import com.forwiz.pms.domain.message.service.MessageService;
import com.forwiz.pms.domain.organization.service.OrganizationService;
import com.forwiz.pms.domain.page.PageCalculator;
import com.forwiz.pms.domain.rank.service.RankService;

import com.forwiz.pms.domain.user.service.UserService;
import com.forwiz.pms.web.message.controller.MessageController;
import com.forwiz.pms.web.organization.controller.OrganizationController;
import com.forwiz.pms.web.rank.RankController;
import com.forwiz.pms.web.user.controller.UserInfoChangeController;
import com.forwiz.pms.web.user.controller.UserSettingController;;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;

import org.springframework.mock.web.MockFilterConfig;
import org.springframework.security.config.BeanIds;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.ServletException;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;


@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(controllers = {UserSettingController.class, UserInfoChangeController.class, OrganizationController.class, MessageController.class, RankController.class})
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
    protected MessageService messageService;
    @MockBean
    protected MessageFileStore messageFileStore;
    @MockBean
    protected PageCalculator pageCalculator;
    @MockBean
    protected RankService rankService;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext) throws ServletException {
        DelegatingFilterProxy delegateProxyFilter = new DelegatingFilterProxy();
        delegateProxyFilter.init(
                new MockFilterConfig(webApplicationContext.getServletContext(), BeanIds.SPRING_SECURITY_FILTER_CHAIN));
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }
}
