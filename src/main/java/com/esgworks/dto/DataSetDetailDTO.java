package com.esgworks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;


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
    private Map<String, Object> chartProperties;
}
