package com.esgworks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartDTO {
    private String chartId;
    private String corporationId;
    private String chartName;
    private String options;
    private LocalDate updatedAt;
    private String updatedBy;
    private LocalDate createdAt;
    private String createdBy;
}

