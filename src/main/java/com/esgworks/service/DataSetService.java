package com.esgworks.service;

import com.esgworks.domain.Chart;
import com.esgworks.domain.DataSet;
import com.esgworks.domain.ESGData;
import com.esgworks.dto.DataSetDTO;
import com.esgworks.dto.DataSetDetailDTO;
import com.esgworks.dto.ESGDataDTO;
import com.esgworks.exceptions.DuplicateException;
import com.esgworks.exceptions.NotFoundException;
import com.esgworks.repository.DataSetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataSetService {
    private final DataSetRepository dataSetRepository;
    private final ESGDataService esgDataService;


    public List<DataSetDTO> getAllDataSets() {
        return dataSetRepository.findAll().stream()
                .map(DataSet::toDTO)
                .toList();
    }

    public List<DataSetDetailDTO> getDetailedDataSetsByChartId(String chartId) {
        List<DataSet> dataSets = dataSetRepository.findAllByChartId(chartId);
        return dataSets.stream().map(dataSet -> {
            List<ESGDataDTO> esgDataDTOList = dataSet.getEsgDataIdList().stream()
                    .map(id -> esgDataService.getESGDataById(id))
                    .toList();

            return dataSet.toDetailDTO(esgDataDTOList);
        }).toList();
    }

    // TYPE별 DateSet 조회
    public List<DataSetDetailDTO> getDetailedDataSetsByChartIdAndType(String chartId, String type) {
        List<DataSet> dataSets = dataSetRepository.findAllByChartId(chartId);
        return dataSets.stream().map(dataSet -> {
            List<ESGDataDTO> esgDataDTOList = dataSet.getEsgDataIdList().stream()
                    .map(id -> esgDataService.getESGDataById(id))
                    .toList();

            return dataSet.toDetailDTO(esgDataDTOList);
        }).toList();
    }

    public List<DataSetDTO> getDataSetsByChartId(String chartId) {
        return dataSetRepository.findAllByChartId(chartId).stream()
                .map(DataSet::toDTO)
                .toList();
    }

    public DataSetDTO getDataSetById(String dataSetId) {
        DataSet dataSet = dataSetRepository.findById(dataSetId).orElseThrow(() -> new RuntimeException("없는 데이터셋입니다."));
        return dataSet.toDTO();
    }

    public DataSetDTO createDataSet(Map<String, Object> data) {
        log.info(data.toString());
        DataSetDTO dataSetDTO = new DataSetDTO();

        dataSetDTO.setChartId(data.get("chartId").toString());
        dataSetDTO.setEsgDataIdList((List<String>) data.getOrDefault("esgDataIdList", new ArrayList<>()));

        // chartProperties Map에 데이터 넣기
        dataSetDTO.setChartProperties(new HashMap<>());

        dataSetDTO.setChartProperty("type", data.get("type"));
        dataSetDTO.setChartProperty("label", data.get("label"));
        dataSetDTO.setChartProperty("backgroundColor", data.get("backgroundColor"));
        dataSetDTO.setChartProperty("borderColor", data.get("borderColor"));
        dataSetDTO.setChartProperty("borderWidth", data.get("borderWidth"));
        dataSetDTO.setChartProperty("barPercentage", data.get("barPercentage"));
        dataSetDTO.setChartProperty("barThickness", data.get("barThickness"));
        dataSetDTO.setChartProperty("tension", data.get("tension"));
        dataSetDTO.setChartProperty("pointStyle", data.get("pointStyle"));
        dataSetDTO.setChartProperty("pointRadius", data.get("pointRadius"));
        dataSetDTO.setChartProperty("pointBackgroundColor", data.get("pointBackgroundColor"));
        dataSetDTO.setChartProperty("pointBorderColor", data.get("pointBorderColor"));
        dataSetDTO.setChartProperty("fill", data.get("fill"));
        dataSetDTO.setChartProperty("offset", data.get("offset"));
        dataSetDTO.setChartProperty("rotation", data.get("rotation"));

        DataSet dataSet = DataSet.fromDTO(dataSetDTO);

        return dataSetRepository.save(dataSet).toDTO();
    }

    public DataSetDTO updateDataSet(String dataSetId, DataSetDTO dto) {
        DataSet existing = dataSetRepository.findDataSetByDataSetId(dataSetId)
                .orElseThrow(() -> new IllegalArgumentException("해당 데이터셋이 존재하지 않습니다."));

        DataSet updated = DataSet.builder()
                .dataSetId(existing.getDataSetId())
                .chartId(dto.getChartId())
                .esgDataIdList(dto.getEsgDataIdList())
                .build();

        dataSetRepository.save(updated);
        return updated.toDTO();
    }

    public void deleteDataSet(String dataSetId) {
        DataSet existing = dataSetRepository.findDataSetByDataSetId(dataSetId)
                .orElseThrow(() -> new IllegalArgumentException("해당 데이터셋이 존재하지 않습니다."));
        dataSetRepository.delete(existing);
    }
}
