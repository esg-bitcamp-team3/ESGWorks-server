package com.esgworks.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDetailDTO {
    private String categoryId;
    private SectionDTO section;
    private UnitDTO unit;
    private String categoryName;
    private String description;
}
