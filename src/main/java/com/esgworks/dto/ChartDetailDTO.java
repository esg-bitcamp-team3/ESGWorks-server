package com.esgworks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartDetailDTO {
    private String chartId;
    private String type;
    private String corporationId;
    private String chartName;
    private List<DataSetDetailDTO> dataSets;
    private String options;
    private String formatOptions;
    private List<String> labels;
    private LocalDate updatedAt;
    private String updatedBy;
    private LocalDate createdAt;
    private String createdBy;
}