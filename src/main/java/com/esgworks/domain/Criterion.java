package com.esgworks.domain;


import com.esgworks.dto.CriterionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "criterion")
public class Criterion {
    @Id
    private String criterionId;

    private String criterionName;

    public CriterionDTO toDTO() {
        return CriterionDTO.builder()
                .criterionId(criterionId)
                .criterionName(criterionName)
                .build();
    }
}
