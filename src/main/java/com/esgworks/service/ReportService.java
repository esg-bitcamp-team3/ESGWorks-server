package com.esgworks.service;

import com.esgworks.domain.Report;
import com.esgworks.dto.*;
import com.esgworks.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final CorporationService corporationService;
    private final UserService userService;

    public Report createReport(ReportRequest dto,String userId) {
        Report report = Report.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .userId(dto.getUserId())
                .corpId(dto.getCorpId())
                .createdBy(userId)
                .createdAt(LocalDateTime.now())
                .updatedBy(userId)
                .updatedAt(LocalDateTime.now())
                .build();
        return reportRepository.save(report);
    }

    public ReportDTO createReportAndReturnDTO(ReportRequest dto, String userId) {
        Report report = createReport(dto, userId);
        CorporationDTO corp = corporationService.getCorporationById(report.getCorpId());
        return ReportDTO.fromEntity(report, corp);
    }


    public List<ReportDetailDTO> getReports() {
        List<Report> reports = reportRepository.findAll();
        return reports.stream()
                .map(report -> {
                    CorporationDTO corpDto =  corporationService.getCorporationById(report.getCorpId());
                    UserDTO createdBy = userService.findById2(report.getCreatedBy());
                    UserDTO updatedBy = userService.findById2(report.getUpdatedBy());
                    return ReportDetailDTO.fromEntity(report, corpDto,createdBy, updatedBy);
                })
                .toList();
    }

    public Report getReportById(String id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("보고서가 존재하지 않습니다."));
    }

    public void deleteReportById(String id) {
        reportRepository.deleteById(id);
    }

    public Report updateReportById(String id, ReportRequest dto, String userId) {
        Report report = getReportById(id);
        report.setTitle(dto.getTitle());
        report.setContent(dto.getContent());
        report.setUpdatedAt(LocalDateTime.now());
        report.setUpdatedBy(userId);
        return reportRepository.save(report);
    }

    public ReportDTO updateReportAndReturnDTO(String id, ReportRequest dto, String userId) {
        Report updated = updateReportById(id, dto, userId);
        CorporationDTO corp = corporationService.getCorporationById(updated.getCorpId());
        return ReportDTO.fromEntity(updated, corp);
    }

    public ReportDTO getReportDTOById(String id) {
        Report report = getReportById(id);
        CorporationDTO corp = corporationService.getCorporationById(report.getCorpId());
        return ReportDTO.fromEntity(report, corp);
    }

    public List<ReportDetailDTO> getReportsByCorpId(String userId, String sortField, String directionStr) {
        String corpId = userService.findById2(userId).getCorpId();

        Sort.Direction direction;
        try {
            direction = Sort.Direction.fromString(directionStr);
        } catch (IllegalArgumentException e) {
            direction = Sort.Direction.DESC; // 기본값 설정
        }

        // 필드 화이트리스트 검증
        Set<String> allowedSortFields = Set.of("createdAt", "updatedAt", "title"); // 필요한 필드만 허용
        if (!allowedSortFields.contains(sortField)) {
            throw new IllegalArgumentException("Invalid sort field: " + sortField);
        }

        Sort sort = Sort.by(direction, sortField);
        List<Report> reports = reportRepository.findAll(sort);
        return reports.stream()
          .map(report -> {
              CorporationDTO corpDto =  corporationService.getCorporationById(corpId);
              UserDTO createdBy = userService.findById2(report.getCreatedBy());
              UserDTO updatedBy = userService.findById2(report.getUpdatedBy());
              return ReportDetailDTO.fromEntity(report, corpDto,createdBy, updatedBy);
          })
          .toList();
    }

}
