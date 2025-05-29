package com.esgworks.bootstrap;

import com.esgworks.domain.Category;
import com.esgworks.dto.CategoryDTO;
import com.esgworks.repository.CategoryRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryDataLoader {

    private final CategoryRepository categoryRepository;

    @PostConstruct
    public void loadGriExcel() {
        try {
            InputStream is = new ClassPathResource("static/gri.xlsx").getInputStream();
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);

            List<CategoryDTO> dtos = new ArrayList<>();

            boolean isFirstRow = true;
            for (Row row : sheet) {
                if (isFirstRow) {
                    isFirstRow = false;
                    continue; // 헤더 건너뜀
                }

                CategoryDTO dto = CategoryDTO.builder()
                        .categoryId(getCellValue(row.getCell(0)))
                        .sectionId(getCellValue(row.getCell(1)))
                        .unitId(getCellValue(row.getCell(2)))
                        .categoryName(getCellValue(row.getCell(3)))
                        .description(getCellValue(row.getCell(4)))
                        .build();

                dtos.add(dto);
            }

            categoryRepository.saveAll(
                    dtos.stream()
                            .map(dto -> Category.builder()
                                    .categoryId(dto.getCategoryId())
                                    .sectionId(dto.getSectionId())
                                    .unitId(dto.getUnitId())
                                    .categoryName(dto.getCategoryName())
                                    .description(dto.getDescription())
                                    .build())
                            .toList()
            );

            System.out.println("✔ GRI 카테고리 자동 등록 완료: " + dtos.size() + "개");

        } catch (Exception e) {
            System.err.println("❌ GRI 데이터 로딩 실패: " + e.getMessage());
        }
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return null;
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> null;
        };
    }
}
