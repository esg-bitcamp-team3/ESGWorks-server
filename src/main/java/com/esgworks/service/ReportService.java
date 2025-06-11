package com.esgworks.service;

import com.esgworks.domain.InterestReports;
import com.esgworks.domain.Report;
import com.esgworks.dto.CorporationDTO;
import com.esgworks.dto.InterestReportsDTO;
import com.esgworks.dto.ReportDTO;
import com.esgworks.dto.ReportRequest;
import com.esgworks.dto.*;
import com.esgworks.repository.InterestReportsRepository;
import com.esgworks.repository.ReportRepository;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final CorporationService corporationService;
    private final InterestReportsService interestReportsService;
    private final UserService userService;
    private final InterestReportsRepository interestReportsRepository;

    public Report createReport(ReportRequest dto,String userId) {
        Report report = Report.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .userId(dto.getUserId())
                .corpId(dto.getCorpId())
                .createdBy(userId)
                .createdAt(LocalDateTime.now())
                .updatedBy(userId)
                .updatedAt(LocalDateTime.now())
                .build();
        return reportRepository.save(report);
    }

    public ReportDTO createReportAndReturnDTO(ReportRequest dto, String userId) {
        Report report = createReport(dto, userId);
        CorporationDTO corp = corporationService.getCorporationById(report.getCorpId());
        InterestReportsDTO interestReportsDTO = interestReportsService.getInterestReportByUserId(report.getId());
        if(interestReportsDTO == null) {
            return ReportDTO.fromEntity(report, corp, false);
        }else{
            return ReportDTO.fromEntity(report, corp, true);
        }

    }


    public List<ReportDTO> getReports() {
        List<Report> reports = reportRepository.findAll();
        return reports.stream()
                .map(report -> {
                    CorporationDTO corpDto =  corporationService.getCorporationById(report.getCorpId());
                    InterestReportsDTO interestReportsDTO = interestReportsService.getInterestReportByUserId(report.getId());
                    if(interestReportsDTO == null) {
                        return ReportDTO.fromEntity(report, corpDto, false);
                    }else{
                        return ReportDTO.fromEntity(report, corpDto, true);
                    }
                })
                .toList();
    }

    public Report getReportById(String id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("보고서가 존재하지 않습니다."));
    }

    public void deleteReportById(String id) {
        reportRepository.deleteById(id);
    }

    public Report updateReportById(String id, ReportRequest dto, String userId) {
        Report report = getReportById(id);
        report.setTitle(dto.getTitle());
        report.setContent(dto.getContent());
        report.setUpdatedAt(LocalDateTime.now());
        report.setUpdatedBy(userId);
        return reportRepository.save(report);
    }

    public ReportDTO updateReportAndReturnDTO(String id, ReportRequest dto, String userId) {
        Report updated = updateReportById(id, dto, userId);
        CorporationDTO corp = corporationService.getCorporationById(updated.getCorpId());
        InterestReportsDTO interestReportsDTO = interestReportsService.getInterestReportByUserId(updated.getId());
        if(interestReportsDTO == null) {
            return ReportDTO.fromEntity(updated, corp, false);
        }else{
            return ReportDTO.fromEntity(updated, corp, true);
        }
    }

    public ReportDTO getReportDTOById(String id) {
        Report report = getReportById(id);
        CorporationDTO corp = corporationService.getCorporationById(report.getCorpId());
        InterestReportsDTO interestReportsDTO = interestReportsService.getInterestReportByUserId(report.getId());
        if(interestReportsDTO == null) {
            return ReportDTO.fromEntity(report, corp, false);
        }else{
            return ReportDTO.fromEntity(report, corp, true);
        }
    }

    public List<ReportDetailDTO> getReportsByCorpId(String userId, String sortField, String directionStr) {
        String corpId = userService.findById2(userId).getCorpId();

        Sort.Direction direction;
        try {
            direction = Sort.Direction.fromString(directionStr);
        } catch (IllegalArgumentException e) {
            direction = Sort.Direction.DESC; // 기본값 설정
        }

        // 필드 화이트리스트 검증
        Set<String> allowedSortFields = Set.of("createdAt", "updatedAt", "title"); // 필요한 필드만 허용
        if (!allowedSortFields.contains(sortField)) {
            throw new IllegalArgumentException("Invalid sort field: " + sortField);
        }

        Sort sort = Sort.by(direction, sortField);
        List<Report> reports = reportRepository.findAll(sort);
        return reports.stream()
          .map(report -> {
              CorporationDTO corpDto =  corporationService.getCorporationById(corpId);
              UserDTO createdBy = userService.findById2(report.getCreatedBy());
              UserDTO updatedBy = userService.findById2(report.getUpdatedBy());
              InterestReportsDTO interestReportsDTO = interestReportsService.getInterestReportByUserId(report.getId());
              if(interestReportsDTO == null) {
                  return ReportDetailDTO.fromEntity(report, corpDto, createdBy, updatedBy, false);
              }else{
                  return ReportDetailDTO.fromEntity(report, corpDto, createdBy, updatedBy, true);
              }
          })
          .toList();
    }

    public List<ReportDetailDTO> getFavoriteReports(String userId, String sortField, String directionStr) {
        String corpId = userService.findById2(userId).getCorpId();

        Sort.Direction direction;
        try {
            direction = Sort.Direction.fromString(directionStr);
        } catch (IllegalArgumentException e) {
            direction = Sort.Direction.DESC; // 기본값 설정
        }

        // 필드 화이트리스트 검증
        Set<String> allowedSortFields = Set.of("createdAt", "updatedAt", "title"); // 필요한 필드만 허용
        if (!allowedSortFields.contains(sortField)) {
            throw new IllegalArgumentException("Invalid sort field: " + sortField);
        }

        Sort sort = Sort.by(direction, sortField);
        List<Report> reports = reportRepository.findAll(sort);
        return reports.stream()
          .map(report -> {
              CorporationDTO corpDto =  corporationService.getCorporationById(corpId);
              UserDTO createdBy = userService.findById2(report.getCreatedBy());
              UserDTO updatedBy = userService.findById2(report.getUpdatedBy());
              InterestReportsDTO interestReportsDTO = interestReportsService.getInterestReportByUserId(report.getId());
              if(interestReportsDTO == null) {
                  return null;
              }else{
                  return ReportDetailDTO.fromEntity(report, corpDto, createdBy, updatedBy, true);
              }
          })
          .filter(Objects::nonNull)
          .toList();
    }

    public List<ReportDTO> searchReports(String keyword, String filter, String userId) {
        if("interest".equals(filter)) {
            //관심 reportId 리스트
            List<InterestReports> interestReports = interestReportsRepository.findByUserId(userId);
            List<String> reportIds = interestReports.stream().map(InterestReports::getReportId).toList();

            //reportsID로 검색
            List<Report> reports = reportRepository.findAllById(reportIds);

            //키워드 추가 필터링
            if(keyword != null && !keyword.isEmpty()) {
                String lowerKeyword = keyword.toLowerCase();
                reports = reports.stream()
                        .filter(report ->
                                (report.getTitle() != null && report.getTitle().toLowerCase().contains(lowerKeyword)) ||
                                        (report.getContent() != null && report.getContent().toLowerCase().contains(lowerKeyword))
                        )
                        .toList();
            }
            return reports.stream()
                    .map(report -> {
                        CorporationDTO corpDto = corporationService.getCorporationById(report.getCorpId());
                        return ReportDTO.fromEntity(report, corpDto, true);
                    })
                    .toList();
        } else {
            List<Report> reports = reportRepository.search(keyword, filter, userId);
            return reports.stream()
                    .map(report -> {
                        CorporationDTO corpDto = corporationService.getCorporationById(report.getCorpId());
                        return ReportDTO.fromEntity(report, corpDto, false);
                    })
                    .toList();
        }

    }
}
