package com.forwiz.pms.domain.organization.repository;

import com.forwiz.pms.domain.organization.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    Optional<Organization> findByOrganizationIdAndOrganizationDelete(Long id, boolean organizationDelete);

    List<Organization> findByOrganizationDelete(boolean delYN);

    @Query("select distinct o from Organization o left join fetch o.userRanks r where o.organizationDelete = ?1 and o.organizationName<>'DEFAULT' and r.rankId in (?2) order by  r.rankWeight")
    List<Organization> findSidebarInfoList(boolean delYN, List<Long> rankIdList);

    Long countByOrganizationNameAndOrganizationDelete(String organizationName, boolean organizationDelete);
}
