//package com.esgworks.bootstrap;
//
//import com.esgworks.domain.Category;
//import com.esgworks.domain.Unit;
//import com.esgworks.dto.CategoryDTO;
//import com.esgworks.dto.UnitDTO;
//import com.esgworks.repository.CategoryRepository;
//import com.esgworks.repository.UnitRepository;
//import com.esgworks.service.addData.AddCategoryService;
//import com.esgworks.service.addData.AddUnitService;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Component;
//
//import java.io.InputStream;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class UnitDataLoader {
//
//    private final UnitRepository unitRepository;
//
//    @PostConstruct
//    public void loadGriCsv() {
//        try {
//            InputStream is = new ClassPathResource("static/unit.csv").getInputStream();
//            List<UnitDTO> units = AddUnitService.parseCsv(is);
//
//            unitRepository.saveAll(
//                    units.stream()
//                            .map(unit -> Unit.builder()
//                                    .unitId(unit.getUnitId())
//                                    .unitName(unit.getUnitName())
//                                    .type(unit.getType())// Example mapping
//                                    .build())
//                            .toList()
//            );
//
//            System.out.println("✔ UNIT 자동 등록 완료: " + units.size() + "개");
//
//        } catch (Exception e) {
//            System.err.println("❌ UNIT 데이터 로딩 실패: " + e.getMessage());
//        }
//    }
//}
