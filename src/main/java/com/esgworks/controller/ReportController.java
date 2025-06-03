package com.esgworks.controller;

import com.esgworks.domain.Report;
import com.esgworks.dto.ReportRequest;
import com.esgworks.repository.ReportRepository;
import com.esgworks.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Slf4j
public class ReportController {
    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<Report> createReport(@RequestBody ReportRequest dto) {
        Report savedReport = reportService.createReport(dto);
        return ResponseEntity.ok(savedReport);
    }

}
