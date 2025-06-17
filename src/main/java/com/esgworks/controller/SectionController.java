package com.esgworks.controller;

import com.esgworks.dto.SectionDTO;
import com.esgworks.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sections")
@RequiredArgsConstructor
public class SectionController {

    private final SectionService sectionService;

    // 전체 섹션 조회
    @GetMapping
    public ResponseEntity<List<SectionDTO>> getAllSections() {
        return ResponseEntity.ok(sectionService.getAllSections());
    }

    // 기준 ID로 섹션 목록 조회
    @GetMapping("/by-criterion/{criterionId}")
    public ResponseEntity<List<SectionDTO>> getSectionsByCriterionId(@PathVariable String criterionId) {
        return ResponseEntity.ok(sectionService.getSectionsByCriterionId(criterionId));
    }

    // 섹션 ID로 단일 섹션 조회
    @GetMapping("/{sectionId}")
    public ResponseEntity<SectionDTO> getSectionById(@PathVariable String sectionId) {
        return ResponseEntity.ok(sectionService.getSectionById(sectionId));
    }

    @GetMapping("/search/{sectionId}")
    public ResponseEntity<List<SectionDTO>> getSectionIdStartsWith(@PathVariable String sectionId) {
        return ResponseEntity.ok(sectionService.getSectionIdStartsWith(sectionId));
    }
}
