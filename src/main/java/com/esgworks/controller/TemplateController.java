package com.esgworks.controller;

import com.esgworks.domain.Template;
import com.esgworks.dto.SectionDTO;
import com.esgworks.dto.TemplateDTO;
import com.esgworks.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/templates")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;

    @GetMapping()
    public List<TemplateDTO> getTemplates() {
        return templateService.getAllTemplates();
    }

    @GetMapping("/id/{templateId}")
    public TemplateDTO getTemplateById(@PathVariable String templateId) {
        return templateService.getTemplateByTemplateId(templateId);
    }

    @PostMapping
    public ResponseEntity<TemplateDTO> createSection(@RequestBody TemplateDTO dto) {
        templateService.createTemplate(dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{templateId}")
    public ResponseEntity<TemplateDTO> deleteTemplate(@PathVariable String templateId) {
        templateService.deleteTemplate(templateId);
        return ResponseEntity.noContent().build();
    }

}
