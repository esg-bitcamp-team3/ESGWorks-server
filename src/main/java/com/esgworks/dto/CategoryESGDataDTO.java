package com.esgworks.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryESGDataDTO {
    private String categoryId;
    private String sectionId;
    private String unitId;
    private String categoryName;
    private String description;
    private ESGDataDTO esgData;
}
