package com.esgworks.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemplateDTO {
    private String templateId;
    private String title;
    private String content;
}
