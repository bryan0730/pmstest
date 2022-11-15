package com.forwiz.pms.domain.organization.repository;

import com.forwiz.pms.domain.organization.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    Optional<Organization> findByOrganizationNameAndOrganizationDelete(String id, boolean organizationDelete);

    List<Organization> findByOrganizationDelete(boolean delYN);

    @Query("select distinct o from Organization o left join fetch o.pmsUsers p where o.organizationDelete = ?1 and o.organizationName<>'DEFAULT' and p.userDeleteYN = false ")
    List<Organization> findSidebarInfoList(boolean delYN);

    Long countByOrganizationNameAndOrganizationDelete(String organizationName, boolean organizationDelete);
}
