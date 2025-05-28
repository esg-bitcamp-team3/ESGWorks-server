package com.esgworks.controller;

import com.esgworks.dto.UnitDTO;
import com.esgworks.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/units")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;

    // 전체 단위(Unit) 목록 조회
    @GetMapping
    public ResponseEntity<List<UnitDTO>> getAllUnits() {
        return ResponseEntity.ok(unitService.getAllUnits());
    }

    // 단위 ID로 단일 조회
    @GetMapping("/{unitId}")
    public ResponseEntity<UnitDTO> getUnitById(@PathVariable String unitId) {
        return ResponseEntity.ok(unitService.getUnitById(unitId));
    }
}
