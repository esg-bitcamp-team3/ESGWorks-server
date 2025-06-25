package com.esgworks.domain;


import com.esgworks.dto.ESGDataDTO;
<<<<<<< HEAD
import com.esgworks.dto.ESGNumberDTO;
=======
import com.esgworks.dto.ESGDataDetailDTO;
import com.esgworks.dto.UnitDTO;
>>>>>>> 510dfc115441f8392888bf851441d7869127fdbb
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

<<<<<<< HEAD
    public ESGNumberDTO toNumberDTO(Double value) {
        return ESGNumberDTO.builder()
=======
    public ESGDataDetailDTO toDetailDTO(UnitDTO unit) {
        return ESGDataDetailDTO.builder()
>>>>>>> 510dfc115441f8392888bf851441d7869127fdbb
                .esgDataId(esgDataId)
                .categoryId(categoryId)
                .corpId(corpId)
                .year(year)
                .value(value)
<<<<<<< HEAD
=======
                .unit(unit)
                .updatedAt(updatedAt)
                .updatedBy(updatedBy)
                .createdAt(createdAt)
                .createdBy(createdBy)
>>>>>>> 510dfc115441f8392888bf851441d7869127fdbb
                .build();
    }

    public void updateValue(String value, String updatedBy) {
        this.value = value;
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = updatedBy;
    }
}
