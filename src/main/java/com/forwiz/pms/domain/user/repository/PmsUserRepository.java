package com.forwiz.pms.domain.user.repository;

import com.forwiz.pms.domain.user.entity.PmsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PmsUserRepository extends JpaRepository<PmsUser, Long> {

    @Query("select p from PmsUser p join fetch p.userRank r " +
            "join fetch r.organization " +
            "where p.userId = ?1 and p.userDeleteYN = ?2")
    Optional<PmsUser> findByUserIdAndUserDeleteYN(String userId, boolean delYN);
    Optional<PmsUser> findById(Long id);

    @Query("select p from PmsUser p join fetch p.userRank r " +
            "join fetch r.organization " +
            "where p.userDeleteYN = ?1")
    List<PmsUser> findByUserDeleteYN(boolean delYN);

    @Query("select p from PmsUser p where p.userId= ?1 and p.userDeleteYN = ?2")
    Optional<PmsUser> findByUserId(String verification, boolean delYN);
    @Query("select distinct p.userRank.rankId from PmsUser p where p.userDeleteYN=?1")
    List<Long> findUserRankIdByDelYn(Boolean delYn);

}
