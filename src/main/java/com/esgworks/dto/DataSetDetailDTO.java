package com.esgworks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataSetDetailDTO {
    private String dataSetId;
    private String chartId;
    private String type;
    private String label;
    private List<ESGDataDTO> esgDataList;
    private String backgroundColor;
    private String borderColor;
    private String borderWidth;
    private String fill;
}
