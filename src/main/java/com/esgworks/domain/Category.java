package com.esgworks.domain;

import com.esgworks.dto.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Setter  //
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "category")
public class Category {
    @Id
    private String categoryId;
    private String sectionId;
    private String unitId;
    private String categoryName;
    private String description;

    public CategoryDTO toDTO() {
        return CategoryDTO.builder()
                .categoryId(categoryId)
                .sectionId(sectionId)
                .unitId(unitId)
                .categoryName(categoryName)
                .description(description)
                .build();
    }

    public CategoryDetailDTO toDetailDTO(SectionDTO section, UnitDTO unit) {
        return CategoryDetailDTO.builder()
                .categoryId(categoryId)
                .section(section)
                .unit(unit)
                .categoryName(categoryName)
                .description(description)
                .build();
    }

}


