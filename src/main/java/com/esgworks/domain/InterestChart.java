package com.esgworks.domain;

import com.esgworks.dto.ChartDTO;
import com.esgworks.dto.InterestChartDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

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
}
