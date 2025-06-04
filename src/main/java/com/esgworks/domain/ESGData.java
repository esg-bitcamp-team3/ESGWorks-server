package com.esgworks.domain;


import com.esgworks.dto.ESGDataDTO;
import lombok.*;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "esgData")
public class ESGData {
    @Id
    private String esgDataId;

    private String categoryId;
    private String corpId;
    private String year;
    private String value;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private LocalDateTime createdAt;
    private String createdBy;

    public ESGDataDTO toDTO() {
        return ESGDataDTO.builder()
                .esgDataId(esgDataId)
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
    public void updateValue(String value, String updatedBy) {
        this.value = value;
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = updatedBy;
    }
}
