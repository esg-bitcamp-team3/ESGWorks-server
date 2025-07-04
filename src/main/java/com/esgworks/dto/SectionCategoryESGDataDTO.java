package com.esgworks.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class    SectionCategoryESGDataDTO {
    private String sectionId;
    private String sectionName;
    private String criterionId;
    private List<CategoryESGDataDTO> categoryESGDataList;
}
