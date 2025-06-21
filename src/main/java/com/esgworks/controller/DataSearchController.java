package com.esgworks.controller;

import com.esgworks.dto.SectionCategoryESGDataDTO;
import com.esgworks.service.DataSearchService;
import com.esgworks.service.GriService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/data")
@RequiredArgsConstructor
public class DataSearchController {
  private final DataSearchService dataSearchService;

  @GetMapping("/search")
  public ResponseEntity<SectionCategoryESGDataDTO> getSelectedSectionCategories(
          @RequestParam(required = false, defaultValue = "2020") String year,
          @RequestParam(required = false) String sectionId) {
    return ResponseEntity.ok(dataSearchService.search(year, sectionId));
  }
}
