package com.springcloud.webclients.api.repository;

import com.springcloud.webclients.api.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyUserRepository extends JpaRepository<MyUser, Long> {

    Optional<MyUser> findByUserId(String userId);
    Optional<MyUser> findById(Long id);

}
