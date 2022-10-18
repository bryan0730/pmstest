package com.springcloud.webclients.api.service;

import com.springcloud.webclients.api.entity.Organization;
import com.springcloud.webclients.api.repository.OragnizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class OragnizationService {

    private final OragnizationRepository oragnizationRepository;

    @Transactional(readOnly = true)
    public Organization findById(String name){
        return oragnizationRepository.findByOrganizationName(name)
                .orElseThrow(() -> new EntityNotFoundException("not found entity"));
    }
}
