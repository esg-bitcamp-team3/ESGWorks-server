package com.esgworks.service;

import com.esgworks.domain.Report;
import com.esgworks.dto.CorporationDTO;
import com.esgworks.dto.ReportDTO;
import com.esgworks.dto.ReportRequest;
import com.esgworks.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.esgworks.service.CorporationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final CorporationService corporationService;

    public Report createReport(ReportRequest dto) {
        Report report = Report.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .userId(dto.getUserId())
                .corpId(dto.getCorpId())
                .build();
        return reportRepository.save(report);
    }

    public ReportDTO createReportAndReturnDTO(ReportRequest dto) {
        Report report = createReport(dto);
        CorporationDTO corp = corporationService.getCorporationById(report.getCorpId());
        return ReportDTO.fromEntity(report, corp);
    }


    public List<ReportDTO> getReports() {
        List<Report> reports = reportRepository.findAll();
        return reports.stream()
                .map(report -> {
                    CorporationDTO corpDto =  corporationService.getCorporationById(report.getCorpId());
                    return ReportDTO.fromEntity(report, corpDto);
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

    public Report updateReportById(String id, ReportRequest dto) {
        Report report = getReportById(id);
        report.setTitle(dto.getTitle());
        report.setContent(dto.getContent());
        return reportRepository.save(report);
    }

    public ReportDTO updateReportAndReturnDTO(String id, ReportRequest dto) {
        Report updated = updateReportById(id, dto);
        CorporationDTO corp = corporationService.getCorporationById(updated.getCorpId());
        return ReportDTO.fromEntity(updated, corp);
    }

    public ReportDTO getReportDTOById(String id) {
        Report report = getReportById(id);
        CorporationDTO corp = corporationService.getCorporationById(report.getCorpId());
        return ReportDTO.fromEntity(report, corp);
    }

}
