package com.springcloud.webclients.api.service;

import com.springcloud.webclients.api.dto.UserDto;
import com.springcloud.webclients.api.entity.MyUser;
import com.springcloud.webclients.api.repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServcie {

    private final MyUserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserDto signUp(UserDto userDto){

        userDto.setUserPw(passwordEncoder.encode(userDto.getUserPw()));
        MyUser myUser = userDto.toEntity();

        myUser = repository.save(myUser);
        UserDto dt = new UserDto(myUser);

        return dt;
    }

}
