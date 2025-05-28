package com.esgworks.service;

import com.esgworks.domain.Unit;
import com.esgworks.dto.UnitDTO;
import com.esgworks.exceptions.NotFoundException;
import com.esgworks.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
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

}
