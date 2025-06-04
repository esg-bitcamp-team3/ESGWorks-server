package com.esgworks.dto;

import com.esgworks.domain.Report;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReportDTO {
    private String id;
    private String title;
    private String content;
    private String userId;
    private CorporationDTO corp;

    public static ReportDTO fromEntity(Report report, CorporationDTO corpDto) {
        return ReportDTO.builder()
                .id(report.getId())
                .title(report.getTitle())
                .content(report.getContent())
                .userId(report.getUserId())
                .corp(corpDto)
                .build();
    }
}
