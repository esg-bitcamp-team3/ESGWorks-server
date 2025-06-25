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
<<<<<<< HEAD
    private Map<String, Object> chartProperties;
=======
    private String backgroundColor;
    private String borderColor;
    private String borderWidth;
    private Boolean fill;
>>>>>>> 510dfc115441f8392888bf851441d7869127fdbb
}
