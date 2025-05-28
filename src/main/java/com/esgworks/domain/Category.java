package com.esgworks.domain;

import com.esgworks.dto.CategoryDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "category")
public class Category {
    @Id
    private String sectionId;

    private String categoryId;
    private String categoryName;
    private String unit;

    public CategoryDTO toDTO() {
        return CategoryDTO.builder()
                .categoryId(categoryId)
                .categoryName(categoryName)
                .unit(unit)
                .build();
    }
}
