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
    private Map<String, Object> chartProperties;

    public void setChartProperty(String key, Object value) {
        this.chartProperties.put(key, value);
    }

    public Object getChartProperty(String key) {
        return this.chartProperties.get(key);
    }
}
