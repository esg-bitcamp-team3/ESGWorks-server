package com.esgworks.service;

import com.esgworks.dto.CategoryESGDataDTO;
import com.esgworks.dto.SectionCategoryESGDataDTO;
import com.esgworks.dto.SectionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GriService {
  private final SectionService sectionService;
  private final CategoryService categoryService;
  private final ESGDataService esgDataService;

  public List<SectionCategoryESGDataDTO> getSectionCategories(String year, String categoryName) {
    List<SectionDTO> sections = sectionService.getAllSections();

    return sections.stream()
      .map(section ->
        SectionCategoryESGDataDTO.builder()
          .sectionId(section.getSectionId())
          .sectionName(section.getSectionName())
          .criterionId(section.getCriterionId())
          .categoryESGDataList(
            categoryService.getCategoryByName(categoryName).stream()
              .map(category ->
                CategoryESGDataDTO.builder()
                  .categoryId(category.getCategoryId())
                  .sectionId(category.getSectionId())
                  .unitId(category.getUnitId())
                  .categoryName(category.getCategoryName())
                  .description(category.getDescription())
                  .esgData(esgDataService.getByCorpIdAndYearAndCategoryId(year, category.getCategoryId()))
                  .build()
              ).toList())
          .build())
      .toList();
  }

  public List<SectionCategoryESGDataDTO> getSectionCategoriesBySectionId(String year, String sectionId, String categoryName) {
    List<SectionDTO> sections = sectionService.getSectionIdStartsWith(sectionId);

    return sections.stream()
      .map(section ->
        SectionCategoryESGDataDTO.builder()
          .sectionId(section.getSectionId())
          .sectionName(section.getSectionName())
          .criterionId(section.getCriterionId())
          .categoryESGDataList(
            categoryService.getCategoryBySectionId(section.getSectionId(), categoryName).stream()
              .map(category ->
                CategoryESGDataDTO.builder()
                  .categoryId(category.getCategoryId())
                  .sectionId(category.getSectionId())
                  .unitId(category.getUnitId())
                  .categoryName(category.getCategoryName())
                  .description(category.getDescription())
                  .esgData(esgDataService.getByCorpIdAndYearAndCategoryId(year, category.getCategoryId()))
                  .build()
              ).toList())
          .build())
      .toList();
  }

//  private List<SectionCategoryESGDataDTO> getSectionCategoryESGDataDTOS(String year, List<SectionDTO> sections, String categoryName) {
//    return sections.stream()
//      .map(section ->
//        SectionCategoryESGDataDTO.builder()
//          .sectionId(section.getSectionId())
//          .sectionName(section.getSectionName())
//          .criterionId(section.getCriterionId())
//          .categoryESGDataList(
//          categoryService.getCategoryBySectionId(section.getSectionId(), categoryName).stream()
//          .map(category ->
//            CategoryESGDataDTO.builder()
//              .categoryId(category.getCategoryId())
//              .sectionId(category.getSectionId())
//              .unitId(category.getUnitId())
//              .categoryName(category.getCategoryName())
//              .description(category.getDescription())
//              .esgData(esgDataService.getByCorpIdAndYearAndCategoryId(year, category.getCategoryId()))
//              .build()
//          ).toList())
//          .build())
//      .toList();
//  }

}
