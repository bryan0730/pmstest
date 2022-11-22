package com.forwiz.pms.domain.user.service;

import com.forwiz.pms.domain.organization.service.OrganizationService;
import com.forwiz.pms.domain.user.dto.Role;
import com.forwiz.pms.domain.user.dto.UserDto;
import com.forwiz.pms.domain.user.dto.UserDuplicatedResponse;
import com.forwiz.pms.domain.user.dto.UserSettingResponse;
import com.forwiz.pms.domain.user.entity.PmsUser;
import com.forwiz.pms.domain.organization.entity.Organization;
import com.forwiz.pms.domain.user.repository.PmsUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    /* JPA repository에 update는 메소드 호출할 필요없이, Entity의 값이 수정되고
    *  수정하는 서비스의 로직이 끝나면(트랜잭션 끝나면) 자동으로 update된다.
    * */
    private final PmsUserRepository userRepository;
    private final OrganizationService organizationService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    @CacheEvict(value = "org", allEntries = true)
    public UserDto signUp(UserDto userDto){

        Organization organization = organizationService.findById(userDto.getUserGroup());
        PmsUser pmsUser = PmsUser.builder()
                .userId(userDto.getUserId())
                .userPw(passwordEncoder.encode(userDto.getUserPw()))
                .userName(userDto.getUserName())
                .auth(userDto.getAuth())
                .organization(organization)
                .userPhoneNumber(userDto.getUserPhoneNumber())
                .userRank(userDto.getUserRank())
                .userDeleteYN(false)
                .build()
                ;

        pmsUser = userRepository.save(pmsUser);

        return new UserDto(pmsUser);
    }

    @Transactional(readOnly = true)
    public List<UserSettingResponse> findAllUser() {

        List<PmsUser> userList = userRepository.findByUserDeleteYN(false);
        return userList.stream()
                .map(UserSettingResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public PmsUser findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("no search member"));
    }

    @PostConstruct
    @Transactional
    public void init(){
        Organization organization = Organization.builder()
                .organizationName("DEFAULT")
                .organizationCode("A0001")
                .organizationDelete(false)
                .build();
        organizationService.save(organization);

        PmsUser pmsUser = PmsUser.builder()
                .userId("admin")
                .userPw(passwordEncoder.encode("1234"))
                .userName(Role.ROLE_ADMIN.getDescription())
                .auth(Role.ROLE_ADMIN)
                .organization(organization)
                .userRank(Role.ROLE_ADMIN.getDescription())
                .userPhoneNumber("000-0000-0000")
                .userDeleteYN(false)
                .build()
                ;
        userRepository.save(pmsUser);
    }

    @Transactional
    @CacheEvict(value = "org", allEntries = true)
    public int delUser(List<Map<String, Long>> mapList) {

        for(Map<String, Long> obj : mapList){
            Long userId = obj.get("id");
            PmsUser pmsUser = userRepository.findById(userId)
                    .orElseThrow(()->new EntityNotFoundException("not found Entity"));

            pmsUser.updateDelYN(true);
        }

        return mapList.size();
    }

    @Transactional
    public UserDuplicatedResponse idDuplicatedCheck(String verification) {

        boolean empty = userRepository.findByUserId(verification, false).isEmpty();
        if (empty){
            return new UserDuplicatedResponse(true, "사용 가능한 ID입니다.");
        }

        return new UserDuplicatedResponse(false, "사용 불가능한 ID입니다.");
    }
}
