package com.esgworks.service;

import com.esgworks.domain.Report;
import com.esgworks.dto.ReportRequest;
import com.esgworks.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
