package com.esgworks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;


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
    private String backgroundColor;
    private String borderColor;
    private String borderWidth;
    private String fill;
}
