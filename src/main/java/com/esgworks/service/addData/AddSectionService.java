package com.esgworks.service.addData;

import com.esgworks.dto.SectionDTO;
import com.esgworks.dto.UnitDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AddSectionService {
    public static List<SectionDTO> parseCsv(InputStream is) {
        try {
            CSVFormat format = CSVFormat.Builder
                    .create(CSVFormat.DEFAULT)
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .build();

            CSVParser parser = new CSVParser(new InputStreamReader(is), format);
            List<SectionDTO> products = new ArrayList<>();
            for (CSVRecord record : parser) {
                SectionDTO s = new SectionDTO();
                s.setSectionId(record.get("section_id"));
                s.setSectionName(record.get("section_name"));
                s.setCriterionId(record.get("criterion_id"));

                products.add(s);
            }
            return products;
        } catch (Exception e) {
            throw new RuntimeException("CSV 파싱 실패: " + e.getMessage());
        }
    }
}
