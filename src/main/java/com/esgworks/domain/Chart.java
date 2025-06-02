package com.esgworks.domain;

import com.esgworks.dto.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "chart")
public class Chart {
    @Id
    private String chartId;

    private String corporationId;
    private String chartName;
    private String options;
    private LocalDate updatedAt;
    private String updatedBy;
    private LocalDate createdAt;
    private String createdBy;

    public ChartDTO toDTO() {
        return ChartDTO.builder()
                .chartId(chartId)
                .chartName(chartName)
                .corporationId(corporationId)
                .options(options)
                .updatedAt(updatedAt)
                .updatedBy(updatedBy)
                .createdAt(createdAt)
                .createdBy(createdBy)
                .build();
    }

    public ChartDetailDTO toDetailDTO(List<DataSetDetailDTO> dataSets) {
        return ChartDetailDTO.builder()
                .chartId(chartId)
                .chartName(chartName)
                .corporationId(corporationId)
                .options(options)
                .dataSets(dataSets)
                .updatedAt(updatedAt)
                .updatedBy(updatedBy)
                .createdAt(createdAt)
                .createdBy(createdBy)
                .build();
    }
}
