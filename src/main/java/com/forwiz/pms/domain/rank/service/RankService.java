package com.forwiz.pms.domain.rank.service;

import com.forwiz.pms.domain.organization.dto.OrganizationListResponse;
import com.forwiz.pms.domain.organization.entity.Organization;
import com.forwiz.pms.domain.organization.repository.OrganizationRepository;

import com.forwiz.pms.domain.rank.dto.RankFormResponse;
import com.forwiz.pms.domain.rank.dto.RankInfoResponse;
import com.forwiz.pms.domain.rank.dto.SaveRankRequest;
import com.forwiz.pms.domain.rank.entity.UserRank;
import com.forwiz.pms.domain.rank.repository.UserRankRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RankService {

    private final UserRankRepository rankRepository;
    private final OrganizationRepository organizationRepository;

    @Transactional(readOnly = true)
    public RankFormResponse makeRankFormData(String orgName) {

        List<OrganizationListResponse> organizationList = organizationRepository.findByOrganizationDelete(false)
                .stream()
                .map(OrganizationListResponse::new)
                .collect(Collectors.toList());

        List<RankInfoResponse> rankList = getRankInfoResponses(orgName);

        return new RankFormResponse(rankList,organizationList);
    }

    @Transactional(readOnly = true)
    public List<RankInfoResponse> getRankInfoResponses(String orgName) {

        Organization organization = organizationRepository.findByOrganizationNameAndOrganizationDelete(orgName, false)
                .orElseThrow(() -> new EntityNotFoundException("not found entity"));

        return rankRepository.findByOrganizationOrderByRankWeight(organization)
                .stream()
                .map(RankInfoResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserRank saveRank(SaveRankRequest saveRankRequest){

        Organization organization = organizationRepository.findById(saveRankRequest.getOrganizationId())
                .orElseThrow(() -> new EntityNotFoundException("not found entity"));
        return rankRepository.save(saveRankRequest.toEntity(organization));
    }

    @Transactional(readOnly = true)
    public UserRank findById(Long id){
        return rankRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("not found entity"));
    }

}
