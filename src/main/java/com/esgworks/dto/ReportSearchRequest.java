package com.esgworks.dto;

import lombok.Data;

@Data
public class ReportSearchRequest {
    private String keyword;
    private String filter;
    private String userId;
}
