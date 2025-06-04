package com.esgworks.service;

import com.esgworks.domain.Chart;
import com.esgworks.domain.Criterion;
import com.esgworks.dto.ChartDTO;
import com.esgworks.dto.ChartDetailDTO;
import com.esgworks.dto.DataSetDetailDTO;
import com.esgworks.exceptions.NotFoundException;
import com.esgworks.repository.ChartRepository;
import com.esgworks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChartService {

    private final ChartRepository chartRepository;
    private final DataSetService dataSetService;
    private final UserService userService;

    // 전체 ESG 데이터 조회
    public List<ChartDTO> getAllCharts() {
        return chartRepository.findAll().stream().map(Chart::toDTO).toList();
    }

    // 차트 ID로 조회
    public ChartDetailDTO getChartByChartId(String chartId) {
        Chart chart = chartRepository.findById(chartId).orElseThrow(() -> new NotFoundException("없는 차트입니다."));

        List<DataSetDetailDTO> dataSetDetailDTOS = dataSetService.getDetailedDataSetsByChartId(chartId);

        return chart.toDetailDTO(dataSetDetailDTOS);
    }

    // corporation ID로 조회
    public List<ChartDetailDTO> getChartsByCorporationId(String corporationId) {
        List<Chart> charts = chartRepository.findAllByCorporationId(corporationId);

        return charts.stream().map(
                chart -> {
                    List<DataSetDetailDTO> dataSetDetailDTOS = dataSetService.getDetailedDataSetsByChartId(chart.getChartId());
                    return chart.toDetailDTO(dataSetDetailDTOS);
                }
        ).toList();

    }

    public List<ChartDetailDTO> getMyCharts(String userId) {
        String corporationId = userService.findById(userId).getCorpId();
        List<Chart> charts = chartRepository.findAllByCorporationId(corporationId);

        return charts.stream().map(
                chart -> {
                    List<DataSetDetailDTO> dataSetDetailDTOS = dataSetService.getDetailedDataSetsByChartId(chart.getChartId());
                    return chart.toDetailDTO(dataSetDetailDTOS);
                }
        ).toList();

    }

    // ESG 데이터 생성
    public ChartDTO createChart(ChartDTO dto, String userId) {
        // 중복 체크
        chartRepository
                .findByCorporationIdAndChartName(dto.getCorporationId(), dto.getChartName())
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("이미 동일한 이름의 차트가 존재합니다.");
                });

        LocalDateTime now = LocalDateTime.now();

        Chart chart = Chart.builder()
                .chartId(dto.getChartId())
                .corporationId(dto.getCorporationId())
                .chartName(dto.getChartName())
                .options(dto.getOptions())
                .createdAt(LocalDate.from(now))
                .createdBy(userId)
                .updatedAt(LocalDate.from(now))
                .updatedBy(userId)
                .build();

        chartRepository.save(chart);
        return chart.toDTO();
    }


    public ChartDTO updateChart(String chartId, ChartDTO dto, String userId) {
        Chart existing = chartRepository.findByChartId(chartId)
                .orElseThrow(() -> new IllegalArgumentException("해당 차트가 존재하지 않습니다."));
        LocalDateTime now = LocalDateTime.now();

        Chart updated = Chart.builder()
                .chartId(existing.getChartId())
                .corporationId(dto.getCorporationId())
                .chartName(dto.getChartName())
                .options(dto.getOptions())
                .createdAt(existing.getCreatedAt())
                .createdBy(existing.getCreatedBy())
                .updatedAt(LocalDate.from(now))
                .updatedBy(userId)
                .build();

        chartRepository.save(updated);
        return updated.toDTO();
    }

    public void deleteChart(String chartId) {
        Chart existing = chartRepository.findByChartId(chartId)
                .orElseThrow(() -> new IllegalArgumentException("해당 차트가 존재하지 않습니다."));
        chartRepository.delete(existing);
    }
}

