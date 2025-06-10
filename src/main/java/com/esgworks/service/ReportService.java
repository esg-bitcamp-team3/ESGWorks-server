package com.esgworks.service;

import com.esgworks.domain.Report;
import com.esgworks.dto.CorporationDTO;
import com.esgworks.dto.InterestReportsDTO;
import com.esgworks.dto.ReportDTO;
import com.esgworks.dto.ReportRequest;
import com.esgworks.repository.InterestReportsRepository;
import com.esgworks.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final CorporationService corporationService;
    private final InterestReportsService interestReportsService;

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
        InterestReportsDTO interestReportsDTO = interestReportsService.getInterestReportByUserId(report.getId());
        if(interestReportsDTO == null) {
            return ReportDTO.fromEntity(report, corp, false);
        }else{
            return ReportDTO.fromEntity(report, corp, true);
        }

    }


    public List<ReportDTO> getReports() {
        List<Report> reports = reportRepository.findAll();
        return reports.stream()
                .map(report -> {
                    CorporationDTO corpDto =  corporationService.getCorporationById(report.getCorpId());
                    InterestReportsDTO interestReportsDTO = interestReportsService.getInterestReportByUserId(report.getId());
                    if(interestReportsDTO == null) {
                        return ReportDTO.fromEntity(report, corpDto, false);
                    }else{
                        return ReportDTO.fromEntity(report, corpDto, true);
                    }
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
        InterestReportsDTO interestReportsDTO = interestReportsService.getInterestReportByUserId(updated.getId());
        if(interestReportsDTO == null) {
            return ReportDTO.fromEntity(updated, corp, false);
        }else{
            return ReportDTO.fromEntity(updated, corp, true);
        }
    }

    public ReportDTO getReportDTOById(String id) {
        Report report = getReportById(id);
        CorporationDTO corp = corporationService.getCorporationById(report.getCorpId());
        InterestReportsDTO interestReportsDTO = interestReportsService.getInterestReportByUserId(report.getId());
        if(interestReportsDTO == null) {
            return ReportDTO.fromEntity(report, corp, false);
        }else{
            return ReportDTO.fromEntity(report, corp, true);
        }
    }

    public List<ReportDTO> searchReports(String keyword, String filter, String userId) {
        List<Report> reports = reportRepository.search(keyword,filter,userId);
        return reports.stream()
                .map(report -> {
                    CorporationDTO corpDto =  corporationService.getCorporationById(report.getCorpId());
                    boolean interestReport = report.getFavoriteUserIds() != null && report.getFavoriteUserIds().contains(userId);
                    return ReportDTO.fromEntity(report, corpDto, interestReport);
                })
                .collect(Collectors.toList());
    }
}
