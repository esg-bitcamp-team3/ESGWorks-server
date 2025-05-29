package com.esgworks.service;

import com.esgworks.domain.Section;
import com.esgworks.dto.SectionDTO;
import com.esgworks.exceptions.NotFoundException;
import com.esgworks.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;
    private final CriterionService criterionService;

    public List<SectionDTO> getAllSections() {
        return sectionRepository.findAll().stream().map(Section::toDTO).toList();
    }

    public List<SectionDTO> getSectionsByCriterionId(String criterionId) {
        criterionService.getCriterionById(criterionId);
        return sectionRepository.findByCriterionId(criterionId).stream().map(Section::toDTO).toList();
    }

    public SectionDTO getSectionById(String sectionId) {
        Section section = sectionRepository.findBySectionId(sectionId).orElseThrow(()->new NotFoundException("없는 섹션입니다."));
        return section.toDTO();
    }
}