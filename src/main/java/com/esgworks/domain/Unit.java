package com.esgworks.domain;


import com.esgworks.dto.UnitDTO;
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
@Document(collection = "unit")
public class Unit {
    @Id
    private String unitId;

    private String unitName;
    private String type;

    public UnitDTO toDTO() {
        return UnitDTO.builder()
                .unitId(unitId)
                .unitName(unitName)
                .type(type)
                .build();
    }
}
