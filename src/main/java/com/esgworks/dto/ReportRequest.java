package com.esgworks.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportRequest {
    private String title;
    private String content;
    private String userId;
    private String corpId;
}
