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
  private final UnitService unitService;

  public List<SectionCategoryESGDataDTO> getSectionCategories(String year, String categoryName) {
    List<SectionDTO> sections = sectionService.getAllSections();

    return getSectionCategoryESGDataDTOS(year, categoryName, sections);
  }

  public List<SectionCategoryESGDataDTO> getSectionCategoriesBySectionId(String year, String sectionId, String categoryName) {
    List<SectionDTO> sections;
    if(sectionId.equals("0")){
      sections = sectionService.getAllSections();
    }else {
      sections = sectionService.getSectionIdStartsWith(sectionId);
    }

    return getSectionCategoryESGDataDTOS(year, categoryName, sections);
  }

  private List<SectionCategoryESGDataDTO> getSectionCategoryESGDataDTOS(String year, String categoryName, List<SectionDTO> sections) {
    return sections.stream()
      .map(section -> getSectionCategoryESGDataDTO(year, categoryName, section)
       )
      .filter(dto -> !dto.getCategoryESGDataList().isEmpty())
      .toList();
  }


  public SectionCategoryESGDataDTO getSectionSelected(String year, String sectionId) {
    SectionDTO section = sectionService.getSectionById(sectionId);
    return getSectionCategoryESGDataDTO(year, null, section);

  }

  private SectionCategoryESGDataDTO getSectionCategoryESGDataDTO (String year, String categoryName, SectionDTO section) {
    return SectionCategoryESGDataDTO.builder()
      .sectionId(section.getSectionId())
      .sectionName(section.getSectionName())
      .criterionId(section.getCriterionId())
      .categoryESGDataList(
        categoryService.getCategoryBySectionId(section.getSectionId(), categoryName).stream()
          .map(category ->
            CategoryESGDataDTO.builder()
              .categoryId(category.getCategoryId())
              .sectionId(category.getSectionId())
              .unit(unitService.getUnitById( category.getUnitId()))
              .categoryName(category.getCategoryName())
              .description(category.getDescription())
              .esgData(esgDataService.getByCorpIdAndYearAndCategoryId(year, category.getCategoryId()))
              .build()
          ).toList())
      .build();
  }

}
