package com.esgworks.service;

import com.esgworks.domain.Criterion;
import com.esgworks.dto.CriterionDTO;
import com.esgworks.dto.UserDTO;
import com.esgworks.repository.CriterionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CriterionService {

    private final CriterionRepository criterionRepository;
    private final UserService userService;

    // 전체 기준 목록 조회
    public List<CriterionDTO> getAllCriteria() {
        return criterionRepository.findAll().stream().map(Criterion::toDTO).toList();
    }

    // ID로 단일 기준 조회
    public CriterionDTO getCriterionById(String criterionId) {
        Criterion criterion = criterionRepository.findById(criterionId).orElseThrow(() -> new RuntimeException("없는 기준입니다."));
        return criterion.toDTO();
    }

    public List<CriterionDTO> getMyCriteria(String userId) {
        UserDTO user = userService.findById2(userId);
        List<Criterion> criteria = criterionRepository.findByCorporationIdIncludingNull(user.getCorpId());
        return criteria.stream().map(Criterion::toDTO).collect(Collectors.toList());
    }
}
