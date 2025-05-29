package com.esgworks.dto;


import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private String categoryId;
    private String sectionId;
    private String unitId;
    private String categoryName;
    private String description;
}
