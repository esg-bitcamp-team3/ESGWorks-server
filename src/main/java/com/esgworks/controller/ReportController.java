package com.esgworks.controller;

import com.esgworks.dto.ReportDTO;
import com.esgworks.dto.ReportDetailDTO;
import com.esgworks.dto.ReportRequest;
import com.esgworks.service.CorporationService;
import com.esgworks.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Slf4j
public class ReportController {
    private final ReportService reportService;
    private final CorporationService corporationService;

    @PostMapping
    public ResponseEntity<ReportDTO> createReport(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ReportRequest dto) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }
        dto.setUserId(userDetails.getUsername());
        ReportDTO savedReport = reportService.createReportAndReturnDTO(dto, userDetails.getUsername());
        return ResponseEntity.ok(savedReport);
    }

    @GetMapping
    public ResponseEntity<List<ReportDTO>> getAll() {
        return ResponseEntity.ok(reportService.getReports());
    }

    @GetMapping("/corp")
    public ResponseEntity<List<ReportDetailDTO>> getAllCorpId(@AuthenticationPrincipal UserDetails userDetails,
                                                              @RequestParam(defaultValue = "createdAt") String sortField,
                                                              @RequestParam(defaultValue = "DESC") String direction) {
        return ResponseEntity.ok(reportService.getReportsByCorpId(userDetails.getUsername(), sortField, direction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(reportService.getReportDTOById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReportDTO> update(@PathVariable String id, @AuthenticationPrincipal UserDetails userDetails ,@RequestBody ReportRequest dto) {

        return ResponseEntity.ok(reportService.updateReportAndReturnDTO(id, dto, userDetails.getUsername()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        reportService.deleteReportById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/interest")
    public ResponseEntity<List<ReportDetailDTO>> getFavorite(@AuthenticationPrincipal UserDetails userDetails,
                                                              @RequestParam(defaultValue = "createdAt") String sortField,
                                                              @RequestParam(defaultValue = "DESC") String direction) {
        return ResponseEntity.ok(reportService.getFavoriteReports(userDetails.getUsername(), sortField, direction));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ReportDetailDTO>> search(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(defaultValue = "all", required = false) String filter,
            @AuthenticationPrincipal UserDetails userDetails) {
        String userId = userDetails != null ? userDetails.getUsername() : null;
        return ResponseEntity.ok(reportService.searchReports(keyword, filter, userId));
    }
}
