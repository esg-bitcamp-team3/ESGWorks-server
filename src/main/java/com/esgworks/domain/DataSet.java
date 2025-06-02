package com.esgworks.domain;

import com.esgworks.dto.ChartDTO;
import com.esgworks.dto.DataSetDTO;
import com.esgworks.dto.DataSetDetailDTO;
import com.esgworks.dto.ESGDataDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "dataSet")
public class DataSet {
    @Id
    private String dataSetId;

    private String chartId;
    private String type;
    private String label;
    private List<String> esgDataIdList;
    private String backgroundColor;
    private String borderColor;
    private String borderWidth;
    private String fill;

    public DataSetDTO toDTO() {
        return DataSetDTO.builder()
                .dataSetId(dataSetId)
                .chartId(chartId)
                .type(type)
                .label(label)
                .esgDataIdList(esgDataIdList)
                .backgroundColor(backgroundColor)
                .borderColor(borderColor)
                .borderWidth(borderWidth)
                .fill(fill)
                .build();
    }

    public DataSetDetailDTO toDetailDTO(List<ESGDataDTO> esgDataList) {
        return DataSetDetailDTO.builder()
                .dataSetId(dataSetId)
                .chartId(chartId)
                .type(type)
                .label(label)
                .esgDataList(esgDataList)
                .backgroundColor(backgroundColor)
                .borderColor(borderColor)
                .borderWidth(borderWidth)
                .fill(fill)
                .build();
    }
}