package com.esgworks.controller;


import com.esgworks.domain.InterestReports;
import com.esgworks.dto.InterestReportsDTO;
import com.esgworks.service.InterestReportsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interest-reports")
@RequiredArgsConstructor
public class InterestReportsController {
    private final InterestReportsService interestReportsService;

    //보고서 목록 조회
    @GetMapping
    public ResponseEntity<List<InterestReportsDTO>> getMyInterestReports() {
        List<InterestReportsDTO> list = interestReportsService.getInterestReportsByUserId();
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{reportId}")
    public ResponseEntity<Boolean> getInterestReport(@PathVariable String reportId) {
        InterestReportsDTO dto = interestReportsService.getInterestReportByUserId(reportId);
        return ResponseEntity.ok(dto != null);
    }

    //보고서 즐겨찾기 추가
    @PostMapping("/{reportId}")
    public ResponseEntity<InterestReportsDTO> addInterestReport(@PathVariable String reportId) {
        InterestReportsDTO dto = interestReportsService.createInterestReport(reportId);
        return ResponseEntity.ok(dto);
    }

    //즐겨찾기 삭제
    @DeleteMapping("/{reportId}")
    public ResponseEntity<InterestReportsDTO> deleteInterestReport(@PathVariable String reportId) {
        interestReportsService.deleteInterestReport(reportId);
        return ResponseEntity.noContent().build();
    }
}
