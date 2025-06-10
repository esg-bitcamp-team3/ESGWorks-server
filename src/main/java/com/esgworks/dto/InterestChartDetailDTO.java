package com.esgworks.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterestChartDetailDTO {
    private String interestChartId;
    private String chartId;
    private ChartDetailDTO chartDetail;
    private String userId;
    private LocalDateTime checkTime;
}
