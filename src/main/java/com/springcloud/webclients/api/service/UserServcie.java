package com.springcloud.webclients.api.service;

import com.springcloud.webclients.api.dto.UserDto;
import com.springcloud.webclients.api.entity.MyUser;
import com.springcloud.webclients.api.entity.Organization;
import com.springcloud.webclients.api.repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServcie {

    /* JPA repository에 update는 메소드 호출할 필요없이, Entity의 값이 수정되고
    *  수정하는 서비스의 로직이 끝나면(트랜잭션 끝나면) 자동으로 update된다.
    * */
    private final MyUserRepository repository;
    private final OrganizationService organizationService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public UserDto signUp(UserDto userDto){

        Organization organization = organizationService.findById(userDto.getUserGroup());
        MyUser myUser = MyUser.builder()
                .userId(userDto.getUserId())
                .userPw(passwordEncoder.encode(userDto.getUserPw()))
                .userName(userDto.getUserName())
                .auth(userDto.getAuth())
                .organization(organization)
                .build()
                ;

        myUser = repository.save(myUser);

        return new UserDto(myUser);
    }

}
