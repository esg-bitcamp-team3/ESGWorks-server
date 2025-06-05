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
import org.springframework.stereotype.Service;

import java.util.List;

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
        List<DataSet> dataSets = dataSetRepository.findAllByChartIdAndType(chartId, type);
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

    public DataSetDTO createDataSet(DataSetDTO dto) {
        if (dto.getDataSetId() != null && dataSetRepository.existsById(dto.getDataSetId())) {
            throw new DuplicateException("이미 존재하는 데이터셋 ID입니다: " + dto.getDataSetId());
        }

        DataSet dataSet = DataSet.builder()
                .dataSetId(dto.getDataSetId())  // 수동 입력받는 경우
                .chartId(dto.getChartId())
                .type(dto.getType())
                .label(dto.getLabel())
                .esgDataIdList(dto.getEsgDataIdList())
                .backgroundColor(dto.getBackgroundColor())
                .borderColor(dto.getBorderColor())
                .borderWidth(dto.getBorderWidth())
                .fill(dto.getFill())
                .build();

        return dataSetRepository.save(dataSet).toDTO();
    }

    public DataSetDTO updateDataSet(String dataSetId, DataSetDTO dto) {
        DataSet existing = dataSetRepository.findDataSetByDataSetId(dataSetId)
                .orElseThrow(() -> new IllegalArgumentException("해당 데이터셋이 존재하지 않습니다."));

        DataSet updated = DataSet.builder()
                .dataSetId(existing.getDataSetId())
                .chartId(dto.getChartId())
                .type(dto.getType())
                .label(dto.getLabel())
                .esgDataIdList(dto.getEsgDataIdList())
                .backgroundColor(dto.getBackgroundColor())
                .borderColor(dto.getBorderColor())
                .borderWidth(dto.getBorderWidth())
                .fill(dto.getFill())
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
