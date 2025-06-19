package com.esgworks.service;

import com.esgworks.domain.Category;
import com.esgworks.domain.Unit;
import com.esgworks.dto.CategoryDTO;
import com.esgworks.dto.UnitDTO;
import com.esgworks.exceptions.NotFoundException;
import com.esgworks.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UnitService {

    private final UnitRepository unitRepository;



    public List<UnitDTO> getAllUnits() {
        return unitRepository.findAll().stream().map(Unit::toDTO).toList();
    }

    public UnitDTO getUnitById(String unitId) {
        Unit unit = unitRepository.findByUnitId(unitId).orElseThrow(()->new NotFoundException("없는 유닛입니다."));
        return unit.toDTO();
    }

    public void createUnit(UnitDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            throw new NotFoundException("로그인하세요");
        }
        Optional<Unit> optionalUnit = unitRepository.findByUnitId(dto.getUnitId());
        if(optionalUnit.isPresent()){
            throw new NotFoundException("이미 존재하는 카테고리입니다.");
        }

        Unit unit = Unit.builder()
                .unitId(dto.getUnitId())
                .unitName(dto.getUnitName())
                .build();
        unitRepository.save(unit);
    }
    public UnitDTO toDto(Unit category) {
        return UnitDTO.builder()
             // ID
                .unitName(category.getUnitName())    // 카테고리 이름
                .unitId(category.getUnitId())                // 단위 ID
                .build();                                    // DTO 완성
    }

    // 카테고리 정보를 업데이트하는 메서드
    public UnitDTO updateUnit(UnitDTO dto) {
        // 현재 로그인한 사용자의 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증 정보가 없거나 로그인 안 된 상태면 예외 발생
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new NotFoundException("로그인하세요");
        }

        // categoryId로 기존 카테고리를 DB에서 찾아오기
        Unit unit = unitRepository.findByUnitId(dto.getUnitId())
                .orElseThrow(() -> new NotFoundException("해당 카테고리를 찾을 수 없습니다."));

        // 기존 카테고리 정보를 전달받은 DTO 값으로 수정
        unit.updateUnit(dto.getUnitName(), dto.getType());

        // 수정된 카테고리를 DB에 저장 (실제로 update 수행)
        Unit updatedUnit = unitRepository.save(unit);

        // 저장된 카테고리 엔티티를 DTO로 변환해서 리턴
        return toDto(updatedUnit);
    }

    public void deleteunit(String unitId) {
        Unit existing = unitRepository.findByUnitId(unitId)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다."));
        unitRepository.delete(existing);
    }
}
