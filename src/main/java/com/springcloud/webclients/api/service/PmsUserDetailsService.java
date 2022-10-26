package com.springcloud.webclients.api.service;

import com.springcloud.webclients.api.dto.PmsUserDetails;
import com.springcloud.webclients.api.entity.PmsUser;
import com.springcloud.webclients.api.repository.PmsUserRepository;
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
public class PmsUserDetailsService implements UserDetailsService {

    private final PmsUserRepository pmsUserRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        Optional<PmsUser> user = pmsUserRepository.findByUserIdAndUserDeleteYN(userId, false);

        return user.map(PmsUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Not found id"));
    }
}
