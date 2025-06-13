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

  @GetMapping("/section")
  public ResponseEntity<List<SectionCategoryESGDataDTO>> getSectionCategories( @RequestParam String year,
                                                                               @RequestParam String sectionId,
                                                                               @RequestParam String categoryName) {
    return ResponseEntity.ok(griService.getSectionCategoriesBySectionId(year,sectionId, categoryName));
  }

  @GetMapping("/select")
  public ResponseEntity<SectionCategoryESGDataDTO> getSelectedSectionCategories( @RequestParam String year,
                                                                               @RequestParam String sectionId) {
    return ResponseEntity.ok(griService.getSectionSelected(year,sectionId));
  }
}
