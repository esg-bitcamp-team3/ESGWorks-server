package com.esgworks.controller;

import com.esgworks.dto.ChartDTO;
import com.esgworks.dto.ChartDetailDTO;
import com.esgworks.service.ChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/charts")
@RequiredArgsConstructor
public class ChartController {

    private final ChartService chartService;

    @GetMapping
    public ResponseEntity<List<ChartDTO>> getAllCharts() {
        return ResponseEntity.ok(chartService.getAllCharts());
    }

    @GetMapping("/{chartId}")
    public ResponseEntity<ChartDetailDTO> getChartById(@PathVariable String chartId) {
        return ResponseEntity.ok(chartService.getChartByChartId(chartId));
    }

    @GetMapping("/corporation/{corpId}")
    public ResponseEntity<List<ChartDetailDTO>> getChartsByCorporationId(@PathVariable String corpId) {
        return ResponseEntity.ok(chartService.getChartsByCorporationId(corpId));
    }

    @GetMapping("/my")
    public ResponseEntity<List<ChartDetailDTO>> getMyCharts(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(chartService.getMyCharts(userDetails.getUsername()));
    }

    // type별 차트 조회
    @GetMapping("/my/{type}")
    public ResponseEntity<List<ChartDetailDTO>> getMyChartsByType(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String type) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(chartService.getMyChartsByUserIdAndType(userDetails.getUsername(), type));
    }



    @PostMapping
    public ResponseEntity<ChartDTO> createChart(@RequestBody ChartDTO dto, Authentication authentication) {
        String userId = authentication.getName();
        try {
            ChartDTO created = chartService.createChart(dto, userId);
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body((ChartDTO) Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{chartId}")
    public ResponseEntity<ChartDTO> updateChart(@PathVariable String chartId, @RequestBody ChartDTO dto, Authentication authentication) {
        String userId = authentication.getName();
        return ResponseEntity.ok(chartService.updateChart(chartId, dto, userId));
    }

    @DeleteMapping("/{chartId}")
    public ResponseEntity<Void> deleteChart(@PathVariable String chartId) {
        chartService.deleteChart(chartId);
        return ResponseEntity.noContent().build();
    }
}
