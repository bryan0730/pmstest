package com.springcloud.webclients.api.repository;

import com.springcloud.webclients.api.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyUserRepository extends JpaRepository<MyUser, Long> {

    Optional<MyUser> findByUserIdAndUserDeleteYN(String userId, boolean delYN);
    Optional<MyUser> findById(Long id);
    List<MyUser> findByUserDeleteYN(boolean delYN);

}
