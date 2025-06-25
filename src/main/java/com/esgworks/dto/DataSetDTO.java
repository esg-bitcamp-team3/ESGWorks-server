package com.esgworks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Map;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataSetDTO {
    private String dataSetId;
    private String chartId;
    private String type;
    private String label;
    private List<String> esgDataIdList;
<<<<<<< HEAD
    private Map<String, Object> chartProperties;

    public void setChartProperty(String key, Object value) {
        this.chartProperties.put(key, value);
    }

    public Object getChartProperty(String key) {
        return this.chartProperties.get(key);
    }
=======
    private String backgroundColor;
    private String borderColor;
    private String borderWidth;
    private Boolean fill;
>>>>>>> 510dfc115441f8392888bf851441d7869127fdbb
}
