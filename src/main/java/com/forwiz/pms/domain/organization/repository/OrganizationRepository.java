package com.forwiz.pms.domain.organization.repository;

import com.forwiz.pms.domain.organization.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    Optional<Organization> findByOrganizationName(String id);

    @Query("select o from Organization o left join fetch o.pmsUsers " +
            "where o.organizationDelete = ?1")
    List<Organization> findByOrganizationDelete(boolean delYN);

    Long countByOrganizationName(String organizationName);
}
