package com.esgworks.service;

import com.esgworks.domain.InterestChart;
import com.esgworks.dto.InterestChartDTO;
import com.esgworks.repository.InterestChartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class InterestChartService {
    private final InterestChartRepository interestChartRepository;

    // 전체 조회 (옵션)
    public List<InterestChartDTO> getAll() {
        return interestChartRepository.findAll().stream()
                .map(InterestChart::toDTO)
                .toList();
    }

    // 사용자 ID로 조회
    public List<InterestChartDTO> getByUserId(String userId) {
        return interestChartRepository.findByUserId(userId).stream()
                .map(InterestChart::toDTO)
                .toList();
    }

    // ID로 단일 조회
    public InterestChartDTO getById(String interestChartId) {
        InterestChart interestChart = interestChartRepository.findById(interestChartId).orElseThrow(() -> new RuntimeException("없는 관심차트입니다."));
        return interestChart.toDTO();
    }

    // 생성
    public InterestChartDTO createInterestChart(InterestChartDTO dto) {
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
    public void deleteInterestChart(String interestChartId) {
        InterestChart existing = interestChartRepository.findById(interestChartId)
                .orElseThrow(() -> new IllegalArgumentException("해당 관심 차트가 존재하지 않습니다."));
        interestChartRepository.delete(existing);
    }
}
