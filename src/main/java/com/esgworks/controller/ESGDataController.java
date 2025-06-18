package com.esgworks.controller;

import com.esgworks.domain.ESGData;
import com.esgworks.dto.CategorizedESGDataListDTO;
import com.esgworks.dto.ESGDataDTO;
import com.esgworks.dto.dataDTO.ESGDataFilterDTO;
import com.esgworks.service.ESGDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/esg-data")
@RequiredArgsConstructor
@Slf4j
public class ESGDataController {

    private final ESGDataService esgDataService;

    // 전체 ESG 데이터 조회
    @GetMapping
    public ResponseEntity<List<ESGDataDTO>> getAllESGData() {

        return ResponseEntity.ok(esgDataService.getAllESGData());
    }

    @GetMapping("/id/{esgDataId}")
public ResponseEntity<ESGDataDTO> getESGDataById(@PathVariable String esgDataId) {
        return ResponseEntity.ok(esgDataService.getESGDataById(esgDataId));
    }

    // 카테고리 ID로 ESG 데이터 조회
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<CategorizedESGDataListDTO> getESGDataByCategoryId(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String categoryId) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userId = userDetails.getUsername();
        return ResponseEntity.ok(esgDataService.getESGDataByCategoryId(categoryId, userId));
    }

    // 기업 ID로 ESG 데이터 조회
    @GetMapping("/corp/{corpId}")
    public ResponseEntity<List<ESGDataDTO>> getESGDataByCorpId(@PathVariable String corpId) {
        return ResponseEntity.ok(esgDataService.getESGDataByCorpId(corpId));
    }

    // 기업 ID + 연도로 단일 ESG 데이터 조회
    @GetMapping("/corp/{corpId}/year/{year}")
    public ResponseEntity<List<ESGDataDTO>> getESGDataByCorpIdAndYear(@PathVariable String corpId, @PathVariable String year) {
        return ResponseEntity.ok(esgDataService.getESGDataByCorpIdAndYear(corpId, year));
    }

    @PostMapping
    public ResponseEntity<ESGDataDTO> createESGData(@RequestBody ESGDataDTO dto, Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        dto.setCreatedBy(userId);
        dto.setCreatedAt(LocalDateTime.now());
        dto.setUpdatedBy(userId);
        dto.setUpdatedAt(LocalDateTime.now());
        return ResponseEntity.ok(esgDataService.createESGData(dto, userId));
    }

    // ESG 데이터 수정
    @PutMapping("/corp/{corpId}/year/{year}")
    public ResponseEntity<ESGDataDTO> updateESGData(@PathVariable String corpId,
                                                    @PathVariable String year,
                                                    @RequestBody ESGDataDTO dto,
                                                    Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        dto.setUpdatedBy(userId);
        dto.setUpdatedAt(LocalDateTime.now());
        return ResponseEntity.ok(esgDataService.updateESGData(corpId, year, dto, userId));
    }

    // ESG 데이터 삭제
    @DeleteMapping("/corp/{corpId}/year/{year}")
    public ResponseEntity<Void> deleteESGData(@PathVariable String corpId,
                                              @PathVariable String year) {
        esgDataService.deleteESGData(corpId, year);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/data-value")
    public ResponseEntity<ESGDataDTO> getESGDataByYearAndCategoryId(@RequestParam String categoryId,
                                                                    @RequestParam(required = false, defaultValue = "2020") String year) {
        ESGDataDTO esgData = esgDataService.getByCorpIdAndYearAndCategoryId(year, categoryId);
        return ResponseEntity.ok(esgData);
    }

    @PatchMapping("/data-value")
    public ResponseEntity<ESGDataDTO> patchESGData(@RequestBody ESGDataFilterDTO dto) {
        log.info(dto.toString());
        return ResponseEntity.ok(esgDataService.patchESGData(dto));
    }
    @PostMapping("/data-value")
    public ResponseEntity<ESGDataDTO> createESGData2(@RequestBody ESGDataFilterDTO dto) {
        return ResponseEntity.ok(esgDataService.createESGData2(dto));
    }
}
