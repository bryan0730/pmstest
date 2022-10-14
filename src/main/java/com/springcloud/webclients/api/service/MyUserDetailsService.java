package com.springcloud.webclients.api.service;

import com.springcloud.webclients.api.dto.MyUserDetails;
import com.springcloud.webclients.api.entity.MyUser;
import com.springcloud.webclients.api.repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final MyUserRepository myUserRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        Optional<MyUser> user = myUserRepository.findByUserId(userId);
//        if(user.isPresent()) return new MyUserDetails(user.get());
//        else throw new UsernameNotFoundException("Not found id");

        return user.map(MyUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Not found id"));
    }
}
