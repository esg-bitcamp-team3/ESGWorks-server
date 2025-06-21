package com.esgworks.service;

import com.esgworks.dto.CategoryDTO;
import com.esgworks.dto.CategoryESGDataDTO;
import com.esgworks.dto.SectionCategoryESGDataDTO;
import com.esgworks.dto.SectionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataSearchService {
  private final SectionService sectionService;
  private final CategoryService categoryService;
  private final ESGDataService esgDataService;
  private final UnitService unitService;

  public SectionCategoryESGDataDTO search(String year, String sectionId) {
    SectionDTO section = sectionService.getSectionById(sectionId);

    List<CategoryDTO> categories = categoryService.getCategoryBySectionId(section.getSectionId());

    return SectionCategoryESGDataDTO.builder()
            .sectionId(section.getSectionId())
            .sectionName(section.getSectionName())
            .criterionId(section.getCriterionId())
            .categoryESGDataList(
                    categories.stream()
                            .map(category ->
                                    CategoryESGDataDTO.builder()
                                            .categoryId(category.getCategoryId())
                                            .sectionId(category.getSectionId())
                                            .unit(unitService.getUnitById(category.getUnitId()))
                                            .categoryName(category.getCategoryName())
                                            .description(category.getDescription())
                                            .esgData(esgDataService.getByCorpIdAndYearAndCategoryId(year, category.getCategoryId()))
                                            .build()
                            ).toList())
            .build();

  }

}
