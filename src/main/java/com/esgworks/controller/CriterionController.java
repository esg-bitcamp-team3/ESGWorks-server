package com.esgworks.controller;

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
}
