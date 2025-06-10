package com.esgworks.domain;

import com.esgworks.dto.InterestReportsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Document(collection = "interestReports")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InterestReports {
  @Id
  private String interestReportId;
  private String reportId;
  private String userId;
  private LocalDateTime checkTime;

  public InterestReportsDTO toDto() {
    return InterestReportsDTO
            .builder()
            .interestReportId(interestReportId)
            .reportId(reportId)
            .userId(userId)
            .checkTime(checkTime)
            .build();
  }
}
