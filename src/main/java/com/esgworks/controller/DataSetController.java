package com.esgworks.controller;

import com.esgworks.dto.DataSetDTO;
import com.esgworks.service.DataSetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/datasets")
@RequiredArgsConstructor
public class DataSetController {

    private final DataSetService dataSetService;

    @GetMapping
    public ResponseEntity<List<DataSetDTO>> getAll() {
        return ResponseEntity.ok(dataSetService.getAllDataSets());
    }

    @GetMapping("/{dataSetId}")
    public ResponseEntity<DataSetDTO> getById(@PathVariable String dataSetId) {
        return ResponseEntity.ok(dataSetService.getDataSetById(dataSetId));
    }

    @GetMapping("/chart/{chartId}")
    public ResponseEntity<List<DataSetDTO>> getByChartId(@PathVariable String chartId) {
        return ResponseEntity.ok(dataSetService.getDataSetsByChartId(chartId));
    }

    @PostMapping
    public ResponseEntity<DataSetDTO> create(@RequestBody DataSetDTO dto) {
        return ResponseEntity.ok(dataSetService.createDataSet(dto));
    }

    @PutMapping("/{dataSetId}")
    public ResponseEntity<DataSetDTO> update(@PathVariable String dataSetId, @RequestBody DataSetDTO dto) {
        return ResponseEntity.ok(dataSetService.updateDataSet(dataSetId, dto));
    }

    @DeleteMapping("/{dataSetId}")
    public ResponseEntity<Void> delete(@PathVariable String dataSetId) {
        dataSetService.deleteDataSet(dataSetId);
        return ResponseEntity.noContent().build();
    }
}
