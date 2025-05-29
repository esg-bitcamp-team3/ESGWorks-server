package com.esgworks.domain;


import com.esgworks.dto.SectionDTO;
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
@Document(collection = "section")
public class Section {
    @Id
    private String sectionId;

    private String sectionName;
    private String criterionId;

    public SectionDTO toDTO() {
        return SectionDTO.builder()
                .sectionId(sectionId)
                .sectionName(sectionName)
                .criterionId(criterionId)
                .build();
    }
}
