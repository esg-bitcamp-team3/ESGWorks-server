package com.esgworks.service;

import com.esgworks.domain.InterestReports;
import com.esgworks.dto.InterestReportsDTO;
import com.esgworks.exceptions.NotFoundException;
import com.esgworks.repository.InterestReportsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InterestReportsService {
  private final InterestReportsRepository interestReportsRepository;
  private final UserService userService;

  public InterestReportsDTO getInterestReportByUserId(String reportId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    userService.findById2(username);
    Optional<InterestReports> interestReports =  interestReportsRepository.findByUserIdAndReportId(username,reportId);
    if(interestReports.isPresent()){
      return interestReports.get().toDto();
    }
    return null;
  }

  public List<InterestReportsDTO> getInterestReportsByUserId() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    // userId ?
    log.info("getInterestReportsByUserId: " + username);
    userService.findById2(username);
    return interestReportsRepository.findByUserId(username).stream().map(
      (interestReports) ->
        InterestReportsDTO.fromEntity(interestReports))
      .toList();
  }

  public InterestReportsDTO createInterestReport(String reportId) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    InterestReports interestReports = InterestReports.builder()
      .reportId(reportId)
      .userId(username)
      .checkTime(LocalDateTime.now())
      .build();
    interestReportsRepository.save(interestReports);
    return InterestReportsDTO.fromEntity(interestReports);

  }

  public void deleteInterestReport(String reportId) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    log.info("deleteInterestReport: user={}, reportId={}" + username, reportId);

    InterestReports interestReports = interestReportsRepository
            .findByUserIdAndReportId(username,reportId)
            .orElseThrow(()-> new IllegalStateException("즐겨찾기 내역이 없습니다."));

    interestReportsRepository.deleteByUserIdAndReportId(username, reportId);
  }
}
