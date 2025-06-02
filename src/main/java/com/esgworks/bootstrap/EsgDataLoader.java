package com.esgworks.bootstrap;

import com.esgworks.domain.ESGData;
import com.esgworks.repository.ESGDataRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EsgDataLoader {

    private final ESGDataRepository esgDataRepository;

    @PostConstruct
    public void loadEsgExcel() {
        try {
            InputStream is = new ClassPathResource("static/esgdata.xlsx").getInputStream();
            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(0);

            List<ESGData> esgDataList = new ArrayList<>();

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) continue;

                String categoryId = getStringValue(row.getCell(0));
                String corpId = getStringValue(row.getCell(1));

                for (int colIndex = 2; colIndex <= 6; colIndex++) {
                    String year = String.valueOf(2018 + colIndex); // 2~6 -> 2020~2024
                    String value = getStringValue(row.getCell(colIndex));

                    if (value == null || value.equalsIgnoreCase("비고") || value.isEmpty()) continue;

                    ESGData esgData = ESGData.builder()
                            .categoryId(categoryId)
                            .corpId(corpId)
                            .year(year)
                            .value(value.replace(",", ""))
                            .createdAt(LocalDate.now())
                            .createdBy(LocalDate.now())
                            .updatedAt(LocalDate.now())
                            .updatedBy(LocalDate.now())
                            .build();

                    esgDataList.add(esgData);
                }
            }

            esgDataRepository.saveAll(esgDataList);
            System.out.println("✔ ESG DATA 자동 등록 완료: " + esgDataList.size() + "개");

        } catch (Exception e) {
            System.err.println("❌ ESG DATA 로딩 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String getStringValue(Cell cell) {
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
