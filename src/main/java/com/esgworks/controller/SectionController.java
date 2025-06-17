package com.esgworks.controller;

import com.esgworks.dto.CategoryDTO;
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
    @PostMapping
    public ResponseEntity<SectionDTO> createSection(@RequestBody SectionDTO dto) {
        sectionService.createSection(dto);
        return ResponseEntity.ok(dto);
    }
    @PutMapping("/{sectionId}")
    public ResponseEntity<SectionDTO> updateSection(@PathVariable Long sectionId, @RequestBody SectionDTO dto) {
        dto.setSectionId(String.valueOf(sectionId));  // 경로에서 받은 categoryId를 DTO에 덮어씀
        SectionDTO updatedDto = sectionService.updateSection(dto);
        return ResponseEntity.ok(updatedDto);  // 진짜 저장된 값 기준으로 응답
    }
    @DeleteMapping("/{sectionId}")
    public ResponseEntity<SectionDTO> deleteSection(@PathVariable String sectionId) {
        sectionService.deleteSection(sectionId);
        return ResponseEntity.noContent().build();
    }
}
