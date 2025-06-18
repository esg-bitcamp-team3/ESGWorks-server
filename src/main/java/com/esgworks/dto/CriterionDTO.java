package com.esgworks.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CriterionDTO {
    private String criterionId;
    private String criterionName;
    private String corporationId;
}
