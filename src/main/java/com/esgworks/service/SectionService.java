package com.esgworks.service;

import com.esgworks.domain.Category;
import com.esgworks.domain.Section;
import com.esgworks.dto.CategoryDTO;
import com.esgworks.dto.SectionDTO;
import com.esgworks.exceptions.NotFoundException;
import com.esgworks.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<SectionDTO> getSectionIdStartsWith(String sectionId) {
        if(sectionId.equals("0") || sectionId.isEmpty()) {
            getAllSections();
        }
      return sectionRepository.findBySectionIdStartsWith(sectionId.substring(0,1)).stream().map(Section::toDTO).toList();
    }

    public void createSection(SectionDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new NotFoundException("로그인하세요");
        }
        Optional<Section> optionalSection = sectionRepository.findBySectionId(dto.getSectionId());
        if(optionalSection.isPresent()){
            throw new NotFoundException("이미 존재하는 섹션입니다.");
        }

        Section section = Section.builder()
                .sectionId(dto.getSectionId())
                .sectionName(dto.getSectionName())
                .criterionId(dto.getCriterionId())
                .build();
        sectionRepository.save(section);

    }
    public SectionDTO updateSection(SectionDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증 정보가 없거나 로그인 안 된 상태면 예외 발생
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new NotFoundException("로그인하세요");
        }

        // categoryId로 기존 카테고리를 DB에서 찾아오기
        Section section = sectionRepository.findBySectionId(dto.getSectionId())
                .orElseThrow(() -> new NotFoundException("해당 섹션을 찾을 수 없습니다."));

        // 기존 카테고리 정보를 전달받은 DTO 값으로 수정
        section.updateSection(dto.getSectionName(), dto.getCriterionId());
        // 수정된 카테고리를 DB에 저장 (실제로 update 수행)
        sectionRepository.save(section);

        // 저장된 카테고리 엔티티를 DTO로 변환해서 리턴
        return section.toDTO();
    }

    public void deleteSection(String sectionId) {
        Section existing = sectionRepository.findBySectionId(sectionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 섹션 존재하지 않습니다."));
        sectionRepository.delete(existing);
    }

}