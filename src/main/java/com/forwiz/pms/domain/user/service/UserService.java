package com.forwiz.pms.domain.user.service;


import com.forwiz.pms.domain.organization.exception.DeleteListEmptyException;
import com.forwiz.pms.domain.rank.dto.RankFormResponse;
import com.forwiz.pms.domain.rank.dto.RankInfoResponse;
import com.forwiz.pms.domain.rank.entity.UserRank;

import com.forwiz.pms.domain.rank.service.RankService;
import com.forwiz.pms.domain.user.dto.*;
import com.forwiz.pms.domain.user.entity.PmsUser;
import com.forwiz.pms.domain.user.exception.IdDuplicatedException;
import com.forwiz.pms.domain.user.repository.PmsUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    /* JPA repository에 update는 메소드 호출할 필요없이, Entity의 값이 수정되고
    *  수정하는 서비스의 로직이 끝나면(트랜잭션 끝나면) 자동으로 update된다.
    * */
    private final PmsUserRepository userRepository;
    private final RankService rankService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    @CacheEvict(value = "org", allEntries = true)
    public void signUp(UserDto userDto){

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

        UserRank userRank = rankService.findById(userDto.getRankId());
        PmsUser pmsUser = PmsUser.builder()
                .userId(userDto.getUserId())
                .userPw(passwordEncoder.encode(userDto.getUserPw()))
                .userName(userDto.getUserName())
                .auth(Role.ROLE_USER)
                .userRank(userRank)
                .userPhoneNumber(userDto.getUserPhoneNumber())
                .userOrganizationName(userRank.getOrganization().getOrganizationName())
                .userDeleteYN(false)
                .build()
                ;
        userRepository.save(pmsUser);
    }

    @Transactional(readOnly = true)
    public List<UserInfoResponse> findAllUserFormData() {
        //사용자 리스트 가져오기(사용자 조회 폼에서만 필요/ 모달에서 필요 X)
         return userRepository.findByUserDeleteYN(false)
                .stream()
                .map(UserInfoResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserSettingFormResponse makeUserSettingFormData(Long orgId) {

        //사용자 리스트 가져옴(삭제안된)
        List<UserInfoResponse> allUserFormData = findAllUserFormData();
        //조직리스트와 해당 조직별 직급 리스트
        RankFormResponse orgAndRankData  = rankService.makeRankFormData(orgId);

        return new UserSettingFormResponse(allUserFormData, orgAndRankData);
    }

    @Transactional(readOnly = true)
    public List<RankInfoResponse> getUserSettingRankInfo(Long orgId){
        return rankService.getRankInfoResponses(orgId);
    }

    @Transactional
    public PmsUser findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("no search member"));
    }

    @Transactional
    @CacheEvict(value = "org", allEntries = true)
    public int delUser(List<Map<String, Long>> mapList) {
        adminVerify(mapList);
        for(Map<String, Long> obj : mapList){
            Long userId = obj.get("id");
            PmsUser pmsUser = userRepository.findById(userId)
                    .orElseThrow(()->new EntityNotFoundException("not found Entity"));

            pmsUser.updateDelYN(true);
        }

        return mapList.size();
    }
    // 내부 검증로직
    private void adminVerify(List<Map<String, Long>> mapList){
        if(mapList.isEmpty() || mapList.stream().anyMatch(map -> map.get("id") == 1)){
            throw new DeleteListEmptyException("삭제할 데이터가 없습니다.");
        }
    }
    @Transactional
    public UserDuplicatedResponse idDuplicatedCheck(String verification) {

        boolean empty = userRepository.findByUserId(verification, false).isEmpty();
        if (empty){
            return new UserDuplicatedResponse(true, "사용 가능한 ID입니다.");
        }

        return new UserDuplicatedResponse(false, "사용 불가능한 ID입니다.");
    }

    @Transactional
    public void changeUserInfo(UserInfoChangeForm userInfoChangeForm) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PmsUserDetails userDetails = (PmsUserDetails) authentication.getPrincipal();
        PmsUser pmsUser = userDetails.getPmsUser();

        pmsUser = userRepository.findById(pmsUser.getId())
                .orElseThrow(()->new EntityNotFoundException("not found Entity"));

        if (Objects.equals(userInfoChangeForm.getUserPw(), "")){
            pmsUser.updatePhoneNumber(userInfoChangeForm.getUserPhoneNumber());
        }else{
            pmsUser.updatePhoneNumberAndPassword(userInfoChangeForm.getUserPhoneNumber(), passwordEncoder.encode(userInfoChangeForm.getUserPw()));
        }

        SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(authentication, pmsUser.getUserId()));
    }

    protected Authentication createNewAuthentication(Authentication currentAuth, String userId) {
        PmsUserDetails userDetails = userRepository.findByUserIdAndUserDeleteYN(userId, false)
                .map(PmsUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Not found id"));
        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(userDetails, currentAuth.getCredentials(), userDetails.getAuthorities());
        newAuth.setDetails(currentAuth.getDetails());
        return newAuth;
    }
}
