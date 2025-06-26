package com.esgworks.service;

import com.esgworks.domain.ESGData;
import com.esgworks.domain.InterestReports;
import com.esgworks.domain.Report;
import com.esgworks.domain.Template;
import com.esgworks.dto.CorporationDTO;
import com.esgworks.dto.InterestReportsDTO;
import com.esgworks.dto.ReportDTO;
import com.esgworks.dto.ReportRequest;
import com.esgworks.dto.*;
import com.esgworks.repository.InterestReportsRepository;
import com.esgworks.repository.ReportRepository;
import com.esgworks.repository.TemplateRepository;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {
  private static final Logger log = LoggerFactory.getLogger(ReportService.class);
  private final ReportRepository reportRepository;
    private final CorporationService corporationService;
    private final InterestReportsService interestReportsService;
    private final UserService userService;
    private final InterestReportsRepository interestReportsRepository;
    private final ESGDataService esgDataService;
    private final TemplateRepository templateRepository;

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
        UserDTO user = userService.findById2(userId);
        dto.setCorpId(user.getCorpId());
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
      return getReportDTOS(reports);
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

    public List<ReportDetailDTO> searchReports(String keyword, String filter, String userId) {
      if ("interest".equals(filter)) {
        //관심 reportId 리스트
        List<InterestReports> interestReports = interestReportsRepository.findByUserId(userId);
        List<String> reportIds = interestReports.stream().map(InterestReports::getReportId).toList();

        //reportsID로 검색
        List<Report> reports = reportRepository.findAllById(reportIds);

        //키워드 추가 필터링
        if (keyword != null && !keyword.isEmpty()) {
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
            return ReportDetailDTO.fromEntity(report, corpDto,
              userService.findById2(report.getCreatedBy()),
              userService.findById2(report.getUpdatedBy()),
              true);
          })
          .toList();
      } else {
          List<Report> reports = reportRepository.search(keyword, filter, userId);
          return getReportDetailDTOS(reports);
      }
    }


  private List<ReportDTO> getReportDTOS(List<Report> reports) {
    return reports.stream()
      .map(report -> {
        CorporationDTO corpDto = corporationService.getCorporationById(report.getCorpId());
        InterestReportsDTO interestReportsDTO = interestReportsService.getInterestReportByUserId(report.getId());
        if(interestReportsDTO == null) {
          return ReportDTO.fromEntity(report, corpDto, false);
        }else {
          return ReportDTO.fromEntity(report, corpDto, true);
        }
      })
      .toList();
  }
  private List<ReportDetailDTO> getReportDetailDTOS(List<Report> reports) {
    return reports.stream()
      .map(report -> {
        CorporationDTO corpDto = corporationService.getCorporationById(report.getCorpId());
        InterestReportsDTO interestReportsDTO = interestReportsService.getInterestReportByUserId(report.getId());
        if(interestReportsDTO == null) {
          return ReportDetailDTO.fromEntity(report, corpDto,
            userService.findById2(report.getCreatedBy()),
            userService.findById2(report.getUpdatedBy()),
            false);
        }else {
          return ReportDetailDTO.fromEntity(report, corpDto,
            userService.findById2(report.getCreatedBy()),
            userService.findById2(report.getUpdatedBy()),
            true);
        }
      })
      .toList();
  }

    public TemplateDTO getTemplate() {
        Template template = templateRepository.findByTemplateId("685b62f694caab35c0f6889d");
        String content = template.getContent();
        Pattern pattern = Pattern.compile("\\{(\\d{7})-(\\d{4})\\}");
        Matcher matcher = pattern.matcher(content);

        List<Map.Entry<String, String>> categoryYearList = new ArrayList<>();
        Set<String> fullPlaceholders = new HashSet<>();
        Map<String, String> resultMap = new HashMap<>();

        while (matcher.find()) {
            String categoryId = matcher.group(1);  // 3050101
            String year = matcher.group(2);        // 2020
            categoryYearList.add(new AbstractMap.SimpleEntry<>(categoryId, year));
            fullPlaceholders.add(matcher.group(0)); // {3050101-2020}
        }

        for (int i = 0; i < categoryYearList.size(); i++) {
            String categoryId = categoryYearList.get(i).getKey();
            String year = categoryYearList.get(i).getValue();
            String key = "{" + categoryId + "-" + year + "}";

            ESGDataDTO esgDataDTO = esgDataService.getByCorpIdAndYearAndCategoryId(year, categoryId);

            String value = (esgDataDTO != null && esgDataDTO.getValue() != null) ? String.valueOf(esgDataDTO.getValue()) : "null";

            resultMap.put(key, value);
        }

//         플레이스홀더 치환
        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            content = content.replace(entry.getKey(), entry.getValue());
        }

        TemplateDTO filledTemplate = new TemplateDTO();
        filledTemplate.setTemplateId(template.getTemplateId());
        filledTemplate.setTitle(template.getTitle());
        filledTemplate.setContent(content);

        return filledTemplate;
    }








}
