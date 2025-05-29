package com.esgworks.service.addData;

import com.esgworks.dto.CriterionDTO;
import com.esgworks.dto.SectionDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AddCriterionService {

    public static List<CriterionDTO> parseCsv(InputStream is) {
        try {
            CSVFormat format = CSVFormat.Builder
                    .create(CSVFormat.DEFAULT)
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .build();

            CSVParser parser = new CSVParser(new InputStreamReader(is), format);
            List<CriterionDTO> products = new ArrayList<>();
            for (CSVRecord record : parser) {
                CriterionDTO c = new CriterionDTO();
                c.setCriterionId(record.get("criterion_id"));
                c.setCriterionName(record.get("criterion_name"));


                products.add(c);
            }
            return products;
        } catch (Exception e) {
            throw new RuntimeException("CSV 파싱 실패: " + e.getMessage());
        }
    }
}
