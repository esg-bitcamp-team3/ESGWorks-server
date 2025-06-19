package com.esgworks.domain;


import com.esgworks.dto.CriterionDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "criterion")
public class Criterion {
    @Id
    private String criterionId;

    private String criterionName;

    private String corporationId;

    public CriterionDTO toDTO() {
        return CriterionDTO.builder()
                .criterionId(criterionId)
                .criterionName(criterionName)
                .corporationId(corporationId)
                .build();
    }
}
