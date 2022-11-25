package com.forwiz.pms.domain.rank.repository;

import com.forwiz.pms.domain.organization.entity.Organization;
import com.forwiz.pms.domain.rank.entity.UserRank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRankRepository extends JpaRepository<UserRank, Long> {

    List<UserRank> findByOrganizationOrderByRankWeight(Organization organization);
}
