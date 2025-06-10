package com.esgworks.service;

import com.esgworks.domain.InterestReports;
import com.esgworks.dto.InterestReportsDTO;
import com.esgworks.repository.InterestReportsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InterestReportsService {
  private final InterestReportsRepository interestReportsRepository;
  private final UserService userService;
  private final ReportService reportService;

  public List<InterestReportsDTO> getInterestReportsByUserId() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    // userId ?
    log.info("getInterestReportsByUserId: " + username);
    userService.findById2(username);
    return interestReportsRepository.findByUserId(username).stream().map(
      (interestReports) ->
        InterestReportsDTO.fromEntity(interestReports
          ,reportService.getReportDTOById(interestReports.getReportId())))
      .toList();
  }

  public InterestReportsDTO createInterestReport(String reportId) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    reportService.getReportById(reportId);
    InterestReports interestReports = InterestReports.builder()
      .reportId(reportId)
      .userId(username)
      .checkTime(LocalDateTime.now())
      .build();
    interestReportsRepository.save(interestReports);
    return InterestReportsDTO.fromEntity(interestReports, reportService.getReportDTOById(reportId));

  }
}
