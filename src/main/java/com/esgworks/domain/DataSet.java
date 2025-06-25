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
import java.util.Map;
import java.util.stream.Collectors;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "dataSet")
public class DataSet {
    @Id
    private String dataSetId;

    private String chartId;
    private List<String> esgDataIdList;

    private Map<String, Object> chartProperties;


    public DataSetDTO toDTO() {
        return DataSetDTO.builder()
                .dataSetId(dataSetId)
                .chartId(chartId)
                .chartProperties(chartProperties)
                .build();
    }

    public DataSetDetailDTO toDetailDTO(List<ESGDataDTO> esgDataDTOList) {
        return DataSetDetailDTO.builder()
                .dataSetId(dataSetId)
                .esgDataList(esgDataDTOList)
                .chartProperties(chartProperties)
                .build();
    }
    public static DataSet fromDTO(DataSetDTO dto) {
        return DataSet.builder()
                .dataSetId(dto.getDataSetId())
                .chartId(dto.getChartId())
                .esgDataIdList(dto.getEsgDataIdList())
                .chartProperties(dto.getChartProperties())
                .build();
    }
}