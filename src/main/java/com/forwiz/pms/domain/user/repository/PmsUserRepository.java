package com.forwiz.pms.domain.user.repository;

import com.forwiz.pms.domain.user.entity.PmsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PmsUserRepository extends JpaRepository<PmsUser, Long> {

    @Query("select p from PmsUser p join fetch p.organization " +
            "where p.userId = ?1 and p.userDeleteYN = ?2")
    Optional<PmsUser> findByUserIdAndUserDeleteYN(String userId, boolean delYN);
    Optional<PmsUser> findById(Long id);

    @Query("select p from PmsUser p join fetch p.organization " +
            "where p.userDeleteYN = ?1")
    List<PmsUser> findByUserDeleteYN(boolean delYN);

    Optional<PmsUser> findByUserId(String verification);

}
