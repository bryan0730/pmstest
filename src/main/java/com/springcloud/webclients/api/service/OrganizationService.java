package com.springcloud.webclients.api.service;

import com.springcloud.webclients.api.dto.AllOrganizationDto;
import com.springcloud.webclients.api.entity.Organization;
import com.springcloud.webclients.api.repository.OragnizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OragnizationRepository oragnizationRepository;

    @Transactional(readOnly = true)
    public Organization findById(String name){
        return oragnizationRepository.findByOrganizationName(name)
                .orElseThrow(() -> new EntityNotFoundException("not found entity"));
    }

    @Transactional
    public List<AllOrganizationDto> findAll(){
        List<Organization> organizations = oragnizationRepository.findAll();

        return organizations.stream()
                .map(AllOrganizationDto::new)
                .collect(Collectors.toList());
    }

    @PostConstruct
    public void init(){
        Organization organization = Organization.builder()
                .organizationName("DEFAULT")
                .build();
        oragnizationRepository.save(organization);
    }
}
