package com.esgworks.domain;

import com.esgworks.dto.TemplateDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Document(collection = "template")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Template {
    @Id
    private String templateId;
    private String title;
    private String content;

    public TemplateDTO toDTO() {
        return TemplateDTO.builder()
                .title(title)
                .content(content)
                .build();
    }

    public Template orElseThrow(Object o) {
        return null;
    }

    public boolean isPresent() {
        return false;
    }
}
