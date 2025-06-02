package com.esgworks.domain;


import com.esgworks.dto.ESGDataDTO;
import lombok.*;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "esgData")
public class ESGData {
    private String categoryId;
    private String corpId;
    private String year;
    private String value;
    private LocalDate updatedAt;
    private LocalDate updatedBy;
    private LocalDate createdAt;
    private LocalDate createdBy;

    public ESGDataDTO toDTO() {
        return ESGDataDTO.builder()
                .categoryId(categoryId)
                .corpId(corpId)
                .year(year)
                .value(value)
                .updatedAt(updatedAt)
                .updatedBy(updatedBy)
                .createdAt(createdAt)
                .createdBy(createdBy)
                .build();
    }
}
