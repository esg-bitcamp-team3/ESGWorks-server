package com.esgworks.controller;

import com.esgworks.dto.CategoryDTO;
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
    @PostMapping
    public ResponseEntity<UnitDTO> createunit(@RequestBody UnitDTO dto) {
        unitService.createUnit(dto);
        return ResponseEntity.ok(dto);
    }
    @PutMapping("/{unitId}")
    public ResponseEntity<UnitDTO> updateUnit(@PathVariable Long unitId, @RequestBody UnitDTO dto) {
        dto.setUnitId(String.valueOf(unitId));  // 경로에서 받은 categoryId를 DTO에 덮어씀
        UnitDTO updatedDto = unitService.updateUnit(dto);
        return ResponseEntity.ok(updatedDto);  // 진짜 저장된 값 기준으로 응답
    }
    @DeleteMapping("/{unitId}")
    public ResponseEntity<UnitDTO> deleteUnit(@PathVariable String unitId) {
        unitService.deleteunit(unitId);
        return ResponseEntity.noContent().build();
    }

}
