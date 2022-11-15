package com.forwiz.pms.domain.organization.service;

import com.forwiz.pms.domain.organization.dto.OrganizationListResponse;
import com.forwiz.pms.domain.organization.dto.OrganizationUsersResponse;
import com.forwiz.pms.domain.organization.dto.SaveOrganizationRequest;
import com.forwiz.pms.domain.organization.entity.Organization;
import com.forwiz.pms.domain.organization.repository.OrganizationRepository;
import com.forwiz.pms.domain.organization.DuplicateConfirmation;
import com.forwiz.pms.domain.organization.OrgCodeCreater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Transactional(readOnly = true)
    public Organization findById(String name){
        return organizationRepository.findByOrganizationNameAndOrganizationDelete(name, false)
                .orElseThrow(() -> new EntityNotFoundException("not found entity"));
    }

    @Transactional
    public List<OrganizationListResponse> selectOrganizationList(){
        List<Organization> organizations = organizationRepository.findByOrganizationDelete(false);

        return organizations.stream()
                .map(OrganizationListResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    @Cacheable(value = "org")
    public List<OrganizationUsersResponse> selectSideBarUserList(){
        List<Organization> organizations = organizationRepository.findSidebarInfoList(false);
        log.info("::::::OrganizationService.sideBar select method:::::::::::");

        return organizations.stream()
                .map(OrganizationUsersResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    @CacheEvict(value = "org", allEntries = true)
    public DuplicateConfirmation saveOrganization(SaveOrganizationRequest saveOrganizationRequest) {

        Long rowCount = organizationNameDuplicateCheck(saveOrganizationRequest.getOrganizationName());
        DuplicateConfirmation confirm = DuplicateConfirmation.valueOf(rowCount);

        if(confirm.name().equals("DUPLICATE")){
            return confirm;
        }

        String code = OrgCodeCreater.make(saveOrganizationRequest.getOrganizationName());
        saveOrganizationRequest.setOrganizationCode(code);

        organizationRepository.save(saveOrganizationRequest.toEntity());

        return confirm;
    }


    private Long organizationNameDuplicateCheck(String organizationName){
        Long rowCount = organizationRepository.countByOrganizationNameAndOrganizationDelete(organizationName, false);
        log.info("orgName duplicated check method ::::: row count : {}", rowCount);

        return rowCount;
    }

    @Transactional
    @CacheEvict(value = "org", allEntries = true)
    public int delOrganization(List<Map<String, Long>> mapList){

        for(Map<String, Long> obj : mapList){
            Long orgId = obj.get("id");
            Organization organization = organizationRepository.findById(orgId)
                    .orElseThrow(()->new EntityNotFoundException("not found entity"));

            organization.updateDelYN(true);
        }

        return mapList.size();
    }

    @Transactional
    public void save(Organization organization){
        organizationRepository.save(organization);
    }
}
