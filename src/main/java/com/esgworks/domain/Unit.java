package com.esgworks.domain;


import com.esgworks.dto.UnitDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "unit")
public class Unit {
    @Id
    private String unitId;
    private String unitName;
    private String type;
    private String description;

    public UnitDTO toDTO() {
        return UnitDTO.builder()
                .unitId(unitId)
                .unitName(unitName)
                .type(type)
                .description(description)
                .build();
    }
    public void updateUnit(String unitName, String type) {
        this.unitName = unitName;
        this.type = type;
    }
}
