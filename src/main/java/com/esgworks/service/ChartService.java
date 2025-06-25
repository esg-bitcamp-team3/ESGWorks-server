package com.esgworks.service;

import com.esgworks.domain.Chart;
import com.esgworks.dto.ChartDTO;
import com.esgworks.dto.ChartDetailDTO;
import com.esgworks.dto.DataSetDetailDTO;
import com.esgworks.dto.UserDTO;
import com.esgworks.exceptions.NotFoundException;
import com.esgworks.repository.ChartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
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
        List<Chart> charts = chartRepository.findAllByCorporationIdOrderByCreatedAtDesc(corporationId);

        return charts.stream().map(
                chart -> {
                    List<DataSetDetailDTO> dataSetDetailDTOS = dataSetService.getDetailedDataSetsByChartId(chart.getChartId());
                    return chart.toDetailDTO(dataSetDetailDTOS);
                }
        ).toList();
    }

    // 유저 ID로 차트 조회
    public List<ChartDetailDTO> getMyCharts(String userId) {
        String corporationId = userService.findById(userId).getCorpId();
        List<Chart> charts = chartRepository.findAllByCorporationIdOrderByCreatedAtDesc(corporationId);

        return charts.stream().map(
                chart -> {
                    List<DataSetDetailDTO> dataSetDetailDTOS = dataSetService.getDetailedDataSetsByChartId(chart.getChartId());
                    return chart.toDetailDTO(dataSetDetailDTOS);
                }
        ).toList();
    }

    // 유저 ID & 차트 TYPE별로 차트 조회
    public List<ChartDetailDTO> getMyChartsByUserIdAndType(String userId, String type) {
        String corporationId = userService.findById(userId).getCorpId();
        List<Chart> charts = chartRepository.findAllByCorporationIdOrderByCreatedAtDesc(corporationId);

        return charts.stream()
                .filter(chart -> !dataSetService.getDetailedDataSetsByChartIdAndType(chart.getChartId(), type).isEmpty())
                .map(chart -> getChartByChartId(chart.getChartId()))
                .toList();
    }

    public ChartDetailDTO getMyChartsByChartIdAndType(String chartId, String type) {
        Chart chart = chartRepository.findById(chartId)
                .orElseThrow(() -> new NotFoundException("없는 차트입니다."));

        List<DataSetDetailDTO> dataSetDetailDTOS = dataSetService.getDetailedDataSetsByChartIdAndType(chartId, type);

        if (dataSetDetailDTOS.isEmpty()) {
            return null; // 또는 Optional.empty() 로 감싸는 것도 고려 가능
        }
        return chart.toDetailDTO(dataSetDetailDTOS);
    }


    // 차트 생성
    public ChartDTO createChart(ChartDTO dto, String userId) {

        UserDTO user = userService.findById2(userId);
        dto.setCorporationId(user.getCorpId());
        log.info(dto.toString());

        chartRepository
                .findByCorporationIdAndChartName(user.getCorpId(), dto.getChartName())
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("이미 동일한 이름의 차트가 존재합니다.");
                });

        LocalDateTime now = LocalDateTime.now();


        Chart chart = Chart.fromDTO(dto);

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

