package com.esgworks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ESGDataDTO {
    private String categoryId;
    private String corpId;
    private String year;
    private String value;
    private LocalDate updatedAt;
    private LocalDate updatedBy;
    private LocalDate createdAt;
    private LocalDate createdBy;
}
