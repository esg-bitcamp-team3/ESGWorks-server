package com.esgworks.controller;

import com.esgworks.dto.InterestChartDTO;
import com.esgworks.service.InterestChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interest-charts")
@RequiredArgsConstructor
public class InterestChartController {

    private final InterestChartService interestChartService;

    @GetMapping
    public ResponseEntity<List<InterestChartDTO>> getAll() {
        return ResponseEntity.ok(interestChartService.getAll());
    }

    @GetMapping("/{interestChartId}")
    public ResponseEntity<InterestChartDTO> getById(@PathVariable String interestChartId) {
        return ResponseEntity.ok(interestChartService.getById(interestChartId));
    }

    @GetMapping("/userId")
    public ResponseEntity<List<InterestChartDTO>> getByUserId(Authentication authentication) {
        String userId = authentication.getName();
        return ResponseEntity.ok(interestChartService.getByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<InterestChartDTO> create(@RequestBody InterestChartDTO dto, Authentication authentication) {
        String userId = authentication.getName();
        dto.setUserId(userId);
        return ResponseEntity.ok(interestChartService.createInterestChart(dto));
    }

    @PutMapping("/{interestChartId}")
    public ResponseEntity<InterestChartDTO> update(@PathVariable String interestChartId, @RequestBody InterestChartDTO dto, Authentication authentication) {
        String userId = authentication.getName();
        dto.setUserId(userId);
        return ResponseEntity.ok(interestChartService.updateInterestChart(interestChartId, dto));
    }

    @DeleteMapping("/{interestChartId}")
    public ResponseEntity<Void> delete(@PathVariable String interestChartId) {
        interestChartService.deleteInterestChart(interestChartId);
        return ResponseEntity.noContent().build();
    }
}
