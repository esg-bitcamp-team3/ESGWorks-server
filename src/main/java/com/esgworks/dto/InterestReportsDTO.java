package com.esgworks.dto;

import com.esgworks.domain.InterestReports;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class InterestReportsDTO {
  private String interestReportId;
  private String reportId;
  private String userId;
  private LocalDateTime checkTime;

  public static  InterestReportsDTO fromEntity(InterestReports interestReports) {
    return InterestReportsDTO.builder()
      .interestReportId(interestReports.getInterestReportId())
      .reportId(interestReports.getReportId())
      .userId(interestReports.getUserId())
      .checkTime(interestReports.getCheckTime())
      .build();
  }
}
