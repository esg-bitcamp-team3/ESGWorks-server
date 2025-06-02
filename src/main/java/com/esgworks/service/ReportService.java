package com.esgworks.service;

import com.esgworks.domain.Report;
import com.esgworks.dto.ReportRequest;
import com.esgworks.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;

    public Report createReport(ReportRequest dto) {
        Report report = Report.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .userId(dto.getUserId())
                .corpId(dto.getCorpId())
                .build();
        return reportRepository.save(report);
    }

    public List<Report> getReports() {
        return reportRepository.findAll();
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
}
