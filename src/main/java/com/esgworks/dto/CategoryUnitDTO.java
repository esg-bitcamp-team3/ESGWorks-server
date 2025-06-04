package com.esgworks.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryUnitDTO {
    private String categoryId;
    private String sectionId;
    private UnitDTO unit;
    private String categoryName;
    private String description;
}
