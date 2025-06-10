package com.esgworks.domain;

import com.esgworks.dto.CorporationDTO;
import com.esgworks.dto.ReportDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "reports")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Report {
    @Id
    private String id;
    private String title;
    private String content;
    private String userId;  // User_id 참조
    private String corpId;  // Corporation_id 또는 corpId 참조
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
