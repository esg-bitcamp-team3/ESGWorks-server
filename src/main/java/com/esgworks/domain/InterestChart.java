package com.esgworks.domain;

import com.esgworks.dto.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "interestChart")
public class InterestChart {
    @Id
    private String interestChartId;

    private String chartId;
    private String userId;
    private LocalDateTime checkTime;

    public InterestChartDTO toDTO() {
        return InterestChartDTO.builder()
                .interestChartId(interestChartId)
                .chartId(chartId)
                .userId(userId)
                .checkTime(checkTime)
                .build();
    }

    public InterestChartDetailDTO toDetailDTO(ChartDetailDTO chartDetail) {
        return InterestChartDetailDTO.builder()
                .interestChartId(interestChartId)
                .chartDetail(chartDetail)
                .chartId(chartId)
                .userId(userId)
                .checkTime(checkTime)
                .build();
    }
}
