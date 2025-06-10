package com.esgworks.controller;

import com.esgworks.dto.InterestChartDTO;
import com.esgworks.dto.InterestChartDetailDTO;
import com.esgworks.service.InterestChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    @GetMapping("/my")
    public ResponseEntity<List<InterestChartDetailDTO>> getByUserId(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(interestChartService.getMyInterestCharts(userDetails.getUsername()));
    }

    @GetMapping("/my/{type}")
    public ResponseEntity<List<InterestChartDetailDTO>> getByUserIdAndType(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String type) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(interestChartService.getMyInterestChartsByType(userDetails.getUsername(), type));
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

    @DeleteMapping("/{chartId}")
    public ResponseEntity<Void> delete(@PathVariable String chartId) {
        interestChartService.deleteInterestChart(chartId);
        return ResponseEntity.noContent().build();
    }
}
