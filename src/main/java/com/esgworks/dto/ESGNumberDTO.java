package com.esgworks.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ESGNumberDTO {
    private String categoryId;
    private String corpId;
    private String esgDataId;
    private String year;
    private Double value;
}
