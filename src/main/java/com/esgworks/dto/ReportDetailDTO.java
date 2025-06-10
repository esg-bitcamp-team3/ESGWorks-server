package com.esgworks.dto;

import com.esgworks.domain.Report;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ReportDetailDTO {
    private String id;
    private String title;
    private String content;
    private String userId;
    private CorporationDTO corp;
    private UserDTO createdBy;
    private UserDTO updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isInterestedReport;

    public static ReportDetailDTO fromEntity(Report report,
                                             CorporationDTO corpDto,
                                             UserDTO createdBy,
                                             UserDTO updatedBy,
                                             Boolean isInterestedReport) {
        return ReportDetailDTO.builder()
                .id(report.getId())
                .title(report.getTitle())
                .content(report.getContent())
                .userId(report.getUserId())
                .corp(corpDto)
                .createdBy(createdBy)
                .updatedBy(updatedBy)
                .createdAt(report.getCreatedAt())
                .updatedAt(report.getUpdatedAt())
                .isInterestedReport(isInterestedReport)
                .build();
    }
}
