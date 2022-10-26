package com.springcloud.webclients.api.repository;

import com.springcloud.webclients.api.entity.PmsUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PmsUserRepository extends JpaRepository<PmsUser, Long> {

    Optional<PmsUser> findByUserIdAndUserDeleteYN(String userId, boolean delYN);
    Optional<PmsUser> findById(Long id);
    List<PmsUser> findByUserDeleteYN(boolean delYN);

}
