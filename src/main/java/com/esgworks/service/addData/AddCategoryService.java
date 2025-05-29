package com.esgworks.service.addData;

import com.esgworks.dto.CategoryDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class AddCategoryService {
    public static List<CategoryDTO> parseCsv(InputStream is) {
        try {
            CSVFormat format = CSVFormat.Builder
                    .create(CSVFormat.DEFAULT)
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .build();

            CSVParser parser = new CSVParser(new InputStreamReader(is), format);
            List<CategoryDTO> products = new ArrayList<>();
            for (CSVRecord record : parser) {
                CategoryDTO c = new CategoryDTO();
                c.setCategoryId(record.get("category_id"));
                c.setSectionId(record.get("section_id"));
                c.setUnitId(record.get("unit_id"));
                c.setCategoryName(record.get("category_name"));
                c.setDescription(record.get("description"));
                products.add(c);
            }
            return products;
        } catch (Exception e) {
            throw new RuntimeException("CSV 파싱 실패: " + e.getMessage());
        }
    }
}
