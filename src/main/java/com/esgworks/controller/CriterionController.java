package com.esgworks.controller;

import com.esgworks.dto.CategoryDTO;
import com.esgworks.dto.CriterionDTO;
import com.esgworks.service.CriterionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/criteria")
@RequiredArgsConstructor
public class CriterionController {

    private final CriterionService criterionService;

    // 전체 기준 목록 조회
    @GetMapping
    public ResponseEntity<List<CriterionDTO>> getAllCriteria() {
        List<CriterionDTO> criteria = criterionService.getAllCriteria();
        return ResponseEntity.ok(criteria);
    }

    // 기준 ID로 단일 조회
    @GetMapping("/{criterionId}")
    public ResponseEntity<CriterionDTO> getCriterionById(@PathVariable String criterionId) {
        CriterionDTO criterion = criterionService.getCriterionById(criterionId);
        return ResponseEntity.ok(criterion);
    }
    @PostMapping()
    public ResponseEntity<CriterionDTO> createCriterion(@RequestBody CriterionDTO dto){
        criterionService.createCriterion(dto);
        return ResponseEntity.ok(dto);
    }
    @PutMapping("/{criterionId}")
    public ResponseEntity<CriterionDTO> updateCriterion(@PathVariable Long criterionId, @RequestBody CriterionDTO dto) {
        dto.setCriterionId(String.valueOf(criterionId));  // 경로에서 받은 ID를 DTO에 덮어씀 (Long → Long)
        CriterionDTO updatedDto = criterionService.updateCriterion(dto);
        return ResponseEntity.ok(updatedDto);  // 진짜 저장된 값 기준으로 응답
    }
    @DeleteMapping("/{criterionId}")
    public ResponseEntity<CriterionDTO> deleteCriterion(@PathVariable String criterionId) {
        criterionService.deleteCriterion(criterionId);
        return ResponseEntity.noContent().build();
    }
}
