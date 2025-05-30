package com.esgworks.controller;

import com.esgworks.dto.CorporationDTO;
import com.esgworks.service.CorporationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.esgworks.domain.Corporation;


@RestController
@RequestMapping("/api/corporations")
@RequiredArgsConstructor
public class CorporationController {

    private final CorporationService corporationService;


    // 전체 기업 목록 조회
    @GetMapping
    public ResponseEntity<List<CorporationDTO>> getAllCorporations() {
        List<CorporationDTO> corporations = corporationService.getAllCorporations();
        return ResponseEntity.ok(corporations);
    }

    // 기업 ID로 단일 조회
    @GetMapping("/{corpId}")
    public ResponseEntity<CorporationDTO> getCorporationById(@PathVariable String corpId) {
        CorporationDTO corporation = corporationService.getCorporationById(corpId);
        return ResponseEntity.ok(corporation);
    }

    // 기업 생성
    @PostMapping
    public ResponseEntity<CorporationDTO> createCorporation(@RequestBody CorporationDTO corporationDTO) {
        CorporationDTO createdCorporation = corporationService.createCorporation(corporationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCorporation);
    }

    // 기업 정보 수정
    @PutMapping("/{corpId}")
    public ResponseEntity<CorporationDTO> updateCorporation(
            @PathVariable String corpId,
            @RequestBody CorporationDTO updatedCorporation
    ) {
        CorporationDTO result = corporationService.updateCorporation(corpId, updatedCorporation);
        return ResponseEntity.ok(result);
    }

    // 기업 삭제
    @DeleteMapping("/{corpId}")
    public ResponseEntity<Void> deleteCorporation(@PathVariable String corpId) {
        corporationService.deleteCorporation(corpId);
        return ResponseEntity.noContent().build();
    }
}
