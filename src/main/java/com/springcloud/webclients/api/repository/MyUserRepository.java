package com.springcloud.webclients.api.repository;

import com.springcloud.webclients.api.dto.UserDto;
import com.springcloud.webclients.api.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyUserRepository extends JpaRepository<MyUser, Long> {

    public Optional<MyUser> findByUserId(String userId);
    public Optional<MyUser> findById(Long id);
    MyUser findByAuth(String auth);


}
