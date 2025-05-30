package com.esgworks.bootstrap;

import com.esgworks.domain.Criterion;
import com.esgworks.domain.Section;
import com.esgworks.dto.CriterionDTO;
import com.esgworks.dto.SectionDTO;
import com.esgworks.repository.CriterionRepository;
import com.esgworks.repository.SectionRepository;
import com.esgworks.service.addData.AddCriterionService;
import com.esgworks.service.addData.AddSectionService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;


@Component
@RequiredArgsConstructor
public class CriterionDataLoader {
    private final CriterionRepository criterionRepository;

    @PostConstruct
    public void loadGriCsv() {
        try {
            InputStream is = new ClassPathResource("static/criterion.csv").getInputStream();
            List<CriterionDTO> criterions = AddCriterionService.parseCsv(is);

            criterionRepository.saveAll(
                    criterions.stream()
                            .map(criterion -> Criterion.builder()
                                    .criterionId(criterion.getCriterionId())
                                    .criterionName(criterion.getCriterionName())
                                    .build())
                            .toList()
            );

            System.out.println("✔ CRITERION 자동 등록 완료: " + criterions.size() + "개");

        } catch (Exception e) {
            System.err.println("❌ CRITERION 데이터 로딩 실패: " + e.getMessage());
        }
    }
}
