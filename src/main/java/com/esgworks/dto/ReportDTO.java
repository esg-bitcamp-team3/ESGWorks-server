package com.esgworks.dto;

import com.esgworks.domain.Report;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ReportDTO {
    private String id;
    private String title;
    private String content;
    private String userId;
    private CorporationDTO corp;
    private Boolean isInterestedReport;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ReportDTO fromEntity(Report report, CorporationDTO corpDto, Boolean interestedReport) {
        return ReportDTO.builder()
                .id(report.getId())
                .title(report.getTitle())
                .content(report.getContent())
                .userId(report.getUserId())
                .corp(corpDto)
                .createdBy(report.getCreatedBy())
                .updatedBy(report.getUpdatedBy())
                .createdAt(report.getCreatedAt())
                .updatedAt(report.getUpdatedAt())
                .isInterestedReport(interestedReport)
                .build();
    }
}
