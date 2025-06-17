package com.esgworks.service;

import com.esgworks.domain.Category;
import com.esgworks.domain.Criterion;
import com.esgworks.dto.CategoryDTO;
import com.esgworks.dto.CriterionDTO;
import com.esgworks.dto.UserDTO;
import com.esgworks.exceptions.DuplicateException;
import com.esgworks.exceptions.NotFoundException;
import com.esgworks.repository.CriterionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public void createCriterion(CriterionDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            throw new NotFoundException("로그인하세요");
        }
        Optional<Criterion> optionalCriterion = criterionRepository.findByCriterionId(dto.getCriterionId());
        if (optionalCriterion.isPresent()) {
            throw new DuplicateException("이미 존재하는 기준입니다.");
        }
        Criterion criterion = Criterion.builder()
                .criterionId(dto.getCriterionId())
                .criterionName(dto.getCriterionName())
                .build();

        criterionRepository.save(criterion);
    }
    public CriterionDTO toDto(Criterion criterion) {
        return CriterionDTO.builder()
                .criterionId(criterion.getCriterionId())        // ID
                .criterionName(criterion.getCriterionName())    // 기준 이름
                .build();
    }

    // 카테고리 정보를 업데이트하는 메서드
    public CriterionDTO updateCriterion(CriterionDTO dto) {
        // 현재 로그인한 사용자의 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증 정보가 없거나 로그인 안 된 상태면 예외 발생
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new NotFoundException("로그인하세요");
        }

        // categoryId로 기존 카테고리를 DB에서 찾아오기
        Criterion criterion = criterionRepository.findByCriterionId(dto.getCriterionId())
                .orElseThrow(() -> new NotFoundException("해당 기준을 찾을 수 없습니다."));

        // 기존  기준 정보를 전달받은 DTO 값으로 수정
        criterion.setCriterionName(dto.getCriterionName()); // 이름 수정=


        // 수정된 카테고리를 DB에 저장 (실제로 update 수행)
        Criterion updatedCriterion = criterionRepository.save(criterion);

        // 저장된 카테고리 엔티티를 DTO로 변환해서 리턴
        return toDto(updatedCriterion);
    }

    public void deleteCriterion(String criterionId) {
        Criterion existing = criterionRepository.findByCriterionId(criterionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 기준은 존재하지 않습니다."));
                criterionRepository.delete(existing);
    }
}
