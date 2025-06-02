package com.esgworks.controller;

import com.esgworks.domain.Report;
import com.esgworks.dto.ReportRequest;
import com.esgworks.repository.ReportRepository;
import com.esgworks.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<Report>> getAll() {
        return ResponseEntity.ok(reportService.getReports());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Report> getById(@PathVariable String id) {
        return ResponseEntity.ok(reportService.getReportById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Report> update(@PathVariable String id, @RequestBody ReportRequest dto) {
        return ResponseEntity.ok(reportService.updateReportById(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        reportService.deleteReportById(id);
        return ResponseEntity.noContent().build();
    }
}
