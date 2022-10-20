package com.springcloud.webclients.api.repository;

import com.springcloud.webclients.api.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OragnizationRepository extends JpaRepository<Organization, Long> {

    Optional<Organization> findByOrganizationName(String id);
    List<Organization> findByOrganizationDelete(boolean delYN);
}
