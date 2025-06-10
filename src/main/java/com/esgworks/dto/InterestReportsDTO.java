package com.esgworks.dto;

import com.esgworks.domain.InterestReports;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class InterestReportsDTO {
  private String interestReportId;
  private ReportDTO report;
  private String userId;
  private LocalDateTime checkTime;

  public static  InterestReportsDTO fromEntity(InterestReports interestReports, ReportDTO report) {
    return InterestReportsDTO.builder()
      .interestReportId(interestReports.getInterestReportId())
      .report(report)
      .userId(interestReports.getUserId())
      .checkTime(interestReports.getCheckTime())
      .build();
  }
}
