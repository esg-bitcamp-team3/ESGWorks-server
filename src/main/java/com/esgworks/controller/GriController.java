package com.esgworks.controller;

import com.esgworks.dto.SectionCategoryESGDataDTO;
import com.esgworks.service.GriService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/gri")
@RequiredArgsConstructor
public class GriController {
  private final GriService griService;

  @GetMapping
  public ResponseEntity<List<SectionCategoryESGDataDTO>> getAllSectionCategories(@RequestParam String year, @RequestParam(required = false) String categoryName) {
    return ResponseEntity.ok(griService.getSectionCategories(year,categoryName));
  }

  @GetMapping("/search")
  public ResponseEntity<SectionCategoryESGDataDTO> getSelectedSectionCategories(
          @RequestParam(required = false, defaultValue = "2020") String year,
          @RequestParam(required = false) String sectionId,
          @RequestParam(required = false, defaultValue = "") String categoryName) {
    return ResponseEntity.ok(griService.search(year, sectionId, categoryName));
  }
  @GetMapping("/search-category")
  public ResponseEntity<List<SectionCategoryESGDataDTO>> getSelectedSectionCategoryName(
    @RequestParam(required = false, defaultValue = "2020") String year,
//    @RequestParam(required = false) String sectionId,
    @RequestParam(required = false, defaultValue = "") String categoryName) {
    return ResponseEntity.ok(griService.searchingCategoryName(year, categoryName));
  }
}
