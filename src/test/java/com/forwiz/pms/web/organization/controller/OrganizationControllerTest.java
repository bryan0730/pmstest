package com.forwiz.pms.web.organization.controller;

import com.forwiz.pms.common.ControllerTest;
import com.forwiz.pms.common.WithMockCustomUser;
import com.forwiz.pms.domain.organization.dto.OrganizationListResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.given;
import static com.forwiz.pms.common.fixtures.OrganizationFixtures.*;
import static org.junit.jupiter.api.Assertions.*;

class OrganizationControllerTest extends ControllerTest {

    @Test
    @WithMockCustomUser
    @DisplayName("조직폼 테스트")
    void goOrganizationFormTest() throws Exception {

        List<OrganizationListResponse> responses = List.of(new OrganizationListResponse(케리스()), new OrganizationListResponse(포위즈()));
        given(organizationService.selectOrganizationList()).willReturn(responses);

        mockMvc.perform(get("/admin/organization").with(csrf().asHeader())
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andDo(print());


    }

}