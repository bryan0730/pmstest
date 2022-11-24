package com.forwiz.pms.domain.rank.repository;

import com.forwiz.pms.domain.rank.entity.UserRank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRankRepository extends JpaRepository<UserRank, Long> {
}
