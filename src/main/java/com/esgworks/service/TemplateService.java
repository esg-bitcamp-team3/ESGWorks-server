package com.esgworks.service;

import com.esgworks.domain.Section;
import com.esgworks.domain.Template;
import com.esgworks.dto.SectionDTO;
import com.esgworks.dto.TemplateDTO;
import com.esgworks.exceptions.NotFoundException;
import com.esgworks.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final TemplateRepository templateRepository;

    public List<TemplateDTO> getAllTemplates() {
        return templateRepository.findAll().stream().map(Template::toDTO).toList();
    }

    public TemplateDTO getTemplateByTemplateId(String templateId) {
        return templateRepository.findByTemplateId(templateId).toDTO();
    }

    public void createTemplate(TemplateDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new NotFoundException("로그인하세요");
        }
//        Template existing = templateRepository.findByTemplateId(dto.getTemplateId());
//        if(existing.isPresent()){
//            throw new NotFoundException("이미 존재하는 섹션입니다.");
//        }

        Template template = Template.builder()
                .templateId(dto.getTemplateId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
        templateRepository.save(template);

    }

    public void deleteTemplate(String templateId) {
        Template existing = templateRepository.findByTemplateId(templateId);
//                .orElseThrow(() -> new IllegalArgumentException("해당 섹션 존재하지 않습니다."));
        templateRepository.delete(existing);
    }

}
