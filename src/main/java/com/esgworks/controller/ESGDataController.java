package com.esgworks.controller;

import com.esgworks.dto.ESGDataDTO;
import com.esgworks.service.ESGDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/esg-data")
@RequiredArgsConstructor
public class ESGDataController {

    private final ESGDataService esgDataService;

    // 전체 ESG 데이터 조회
    @GetMapping
    public ResponseEntity<List<ESGDataDTO>> getAllESGData() {
        return ResponseEntity.ok(esgDataService.getAllESGData());
    }

    // 기업 ID로 ESG 데이터 조회
    @GetMapping("/corp/{corpId}")
    public ResponseEntity<List<ESGDataDTO>> getESGDataByCorpId(@PathVariable String corpId) {
        return ResponseEntity.ok(esgDataService.getByCorpId(corpId));
    }

    // 기업 ID + 연도로 단일 ESG 데이터 조회
    @GetMapping("/corp/{corpId}/year/{year}")
    public ResponseEntity<ESGDataDTO> getESGDataByCorpIdAndYear(@PathVariable String corpId, @PathVariable String year) {
        return ResponseEntity.ok(esgDataService.getByCorpIdAndYear(corpId, year));
    }

    // ESG 데이터 생성
    @PostMapping
    public ResponseEntity<ESGDataDTO> createESGData(@RequestBody ESGDataDTO dto) {
        return ResponseEntity.ok(esgDataService.createESGData(dto));
    }

    // ESG 데이터 수정
    @PutMapping("/corp/{corpId}/year/{year}")
    public ResponseEntity<ESGDataDTO> updateESGData(@PathVariable String corpId,
                                                    @PathVariable String year,
                                                    @RequestBody ESGDataDTO dto) {
        return ResponseEntity.ok(esgDataService.updateESGData(corpId, year, dto));
    }

    // ESG 데이터 삭제
    @DeleteMapping("/corp/{corpId}/year/{year}")
    public ResponseEntity<Void> deleteESGData(@PathVariable String corpId,
                                              @PathVariable String year) {
        esgDataService.deleteESGData(corpId, year);
        return ResponseEntity.noContent().build();
    }
}
