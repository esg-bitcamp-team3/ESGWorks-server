//package com.esgworks.bootstrap;
//
//import com.esgworks.domain.Section;
//import com.esgworks.domain.Unit;
//import com.esgworks.dto.SectionDTO;
//import com.esgworks.dto.UnitDTO;
//import com.esgworks.repository.SectionRepository;
//import com.esgworks.repository.UnitRepository;
//import com.esgworks.service.addData.AddSectionService;
//import com.esgworks.service.addData.AddUnitService;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Component;
//
//import java.io.InputStream;
//import java.util.List;
//
//
//@Component
//@RequiredArgsConstructor
//public class SectionDataLoader {
//    private final SectionRepository sectionRepository;
//
//    @PostConstruct
//    public void loadGriCsv() {
//        try {
//            InputStream is = new ClassPathResource("static/section.csv").getInputStream();
//            List<SectionDTO> sections = AddSectionService.parseCsv(is);
//
//            sectionRepository.saveAll(
//                    sections.stream()
//                            .map(section -> Section.builder()
//                                    .sectionId(section.getSectionId())
//                                    .sectionName(section.getSectionName())
//                                    .criterionId(section.getCriterionId())// Example mapping
//                                    .build())
//                            .toList()
//            );
//
//            System.out.println("✔ SECTION 자동 등록 완료: " + sections.size() + "개");
//
//        } catch (Exception e) {
//            System.err.println("❌ SECTION 데이터 로딩 실패: " + e.getMessage());
//        }
//    }
//}
