package com.esgworks.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectionDTO {
    private String sectionId;
    private String sectionName;
    private String criterionId;
}
