package com.esgworks.domain;

import com.esgworks.dto.*;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "chart")
public class Chart {
    @Id
    private String chartId;
    private String type;
    private String corporationId;
    private String chartName;
    private String options;
    private String formatOptions;
    private List<String> labels;
    private LocalDate updatedAt;
    private String updatedBy;
    private LocalDate createdAt;
    private String createdBy;

    public ChartDTO toDTO() {
        return ChartDTO.builder()
                .chartId(chartId)
                .type(type)
                .chartName(chartName)
                .corporationId(corporationId)
                .options(options)
                .formatOptions(formatOptions)
                .labels(labels)
                .updatedAt(updatedAt)
                .updatedBy(updatedBy)
                .createdAt(createdAt)
                .createdBy(createdBy)
                .build();
    }

    public ChartDetailDTO toDetailDTO(List<DataSetDetailDTO> dataSets) {
        return ChartDetailDTO.builder()
                .chartId(chartId)
                .type(type)
                .chartName(chartName)
                .corporationId(corporationId)
                .options(options)
                .formatOptions(formatOptions)
                .labels(labels)
                .dataSets(dataSets)
                .updatedAt(updatedAt)
                .updatedBy(updatedBy)
                .createdAt(createdAt)
                .createdBy(createdBy)
                .build();
    }


    public static Chart fromDTO(ChartDTO dto) {
        return Chart.builder()
                .chartId(dto.getChartId())
                .type(dto.getType())
                .chartName(dto.getChartName())
                .corporationId(dto.getCorporationId())
                .options(dto.getOptions())
                .formatOptions(dto.getFormatOptions())
                .labels(dto.getLabels())
                .updatedAt(dto.getUpdatedAt())
                .updatedBy(dto.getUpdatedBy())
                .createdAt(dto.getCreatedAt())
                .createdBy(dto.getCreatedBy())
                .build();
    }
}
