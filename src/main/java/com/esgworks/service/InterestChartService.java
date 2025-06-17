package com.esgworks.service;

import com.esgworks.domain.InterestChart;
import com.esgworks.dto.ChartDetailDTO;
import com.esgworks.dto.InterestChartDTO;
import com.esgworks.dto.InterestChartDetailDTO;
import com.esgworks.repository.ChartRepository;
import com.esgworks.repository.InterestChartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Slf4j
public class InterestChartService {
    private final InterestChartRepository interestChartRepository;
    private final UserService userService;
    private final ChartRepository chartRepository;
    private final ChartService chartService;


    // 전체 조회 (옵션)
    public List<InterestChartDTO> getAll() {
        return interestChartRepository.findAll().stream()
                .map(InterestChart::toDTO)
                .toList();
    }

    // 사용자 ID로 조회
    public List<InterestChartDetailDTO> getMyInterestCharts(String userId) {
        List<InterestChart> interestCharts = interestChartRepository.findByUserId(userId);

        return interestCharts.stream().map(
                interestChart -> {
                    ChartDetailDTO chartDetailDTO = chartService.getChartByChartId(interestChart.getChartId());
                    return interestChart.toDetailDTO(chartDetailDTO);
                }
        ).toList();
    }

    // 사용자 ID & TYPE별 조회
    public List<InterestChartDetailDTO> getMyInterestChartsByType(String userId, String type) {
        List<InterestChart> interestCharts = interestChartRepository.findByUserId(userId);

        return interestCharts.stream()
                .map(interestChart -> {
                    ChartDetailDTO chartDetailDTO = chartService.getMyChartsByChartIdAndType(interestChart.getChartId(), type);
                    return chartDetailDTO != null ? interestChart.toDetailDTO(chartDetailDTO) : null;
                })
                .filter(Objects::nonNull)
                .toList();
    }

    // ID로 단일 조회
    public InterestChartDTO getById(String interestChartId) {
        InterestChart interestChart = interestChartRepository.findById(interestChartId).orElseThrow(() -> new RuntimeException("없는 관심차트입니다."));
        return interestChart.toDTO();
    }

    // 생성
    public InterestChartDTO createInterestChart(InterestChartDTO dto) {
        // 중복 확인: 해당 유저가 같은 차트를 이미 등록했는지
        boolean exists = interestChartRepository.existsByUserIdAndChartId(dto.getUserId(), dto.getChartId());
        if (exists) {
            throw new IllegalArgumentException("이미 관심 차트에 등록된 항목입니다.");
        }

        InterestChart interestChart = InterestChart.builder()
                .interestChartId(dto.getInterestChartId())
                .chartId(dto.getChartId())
                .userId(dto.getUserId())
                .checkTime(dto.getCheckTime() != null ? dto.getCheckTime() : LocalDateTime.now())
                .build();

        interestChartRepository.save(interestChart);
        return interestChart.toDTO();
    }

    // 수정
    public InterestChartDTO updateInterestChart(String interestChartId, InterestChartDTO dto) {
        InterestChart existing = interestChartRepository.findById(interestChartId)
                .orElseThrow(() -> new IllegalArgumentException("해당 관심 차트가 존재하지 않습니다."));

        InterestChart updated = InterestChart.builder()
                .interestChartId(existing.getInterestChartId())
                .chartId(dto.getChartId())
                .userId(dto.getUserId())
                .checkTime(dto.getCheckTime() != null ? dto.getCheckTime() : LocalDateTime.now())
                .build();

        interestChartRepository.save(updated);
        return updated.toDTO();
    }

    // 삭제
    public void deleteInterestChart(String chartId) {
        InterestChart existing = interestChartRepository.findByChartId(chartId)
                .orElseThrow(() -> new IllegalArgumentException("해당 관심 차트가 존재하지 않습니다."));
        log.info("deleteInterestChart: {}", existing);
        interestChartRepository.delete(existing);
    }
}
