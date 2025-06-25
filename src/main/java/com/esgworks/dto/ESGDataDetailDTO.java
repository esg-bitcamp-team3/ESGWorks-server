package com.esgworks.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ESGDataDetailDTO {
    private String esgDataId;
    private String categoryId;
    private String corpId;
    private String year;
    private String value;
    private UnitDTO unit;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private LocalDateTime createdAt;
    private String createdBy;
}
