package com.forwiz.pms.domain.user.service;

import com.forwiz.pms.domain.organization.service.OrganizationService;
import com.forwiz.pms.domain.user.dto.Role;
import com.forwiz.pms.domain.user.dto.UserDto;
import com.forwiz.pms.domain.user.dto.UserDuplicatedResponse;
import com.forwiz.pms.domain.user.dto.UserSettingResponse;
import com.forwiz.pms.domain.user.entity.PmsUser;
import com.forwiz.pms.domain.organization.entity.Organization;
import com.forwiz.pms.domain.user.exception.IdDuplicatedException;
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

        /*
        해당 예외발생을 Controller가 아닌 Service에서 하는 이유?
        UserSettingController의 delUser 메소드에서는 컨트롤러에서 예외를 발생시켰다.
        => 다른 개발자 또는 다른 곳에서 해당 서비스의 delete 메소드를 사용할 때 검증 로직이 바뀔수도 있고 삭제한다라는 역할만 주기 위해서
        하지만 사용자 등록하는 기능은 서비스에서 검증을 하고 예외를 발생시킨다.
        => 이후에 다른 곳에서 이 서비스 메소드를 사용할때는 무조건 적으로 지켜져야 하는 검증이기 때문이다.
         */

        if (!userRepository.findByUserId(userDto.getUserId(), false).isEmpty()){
            throw new IdDuplicatedException("중복된 ID 요청");
        }

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
