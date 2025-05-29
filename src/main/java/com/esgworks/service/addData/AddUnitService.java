package com.esgworks.service.addData;

import com.esgworks.dto.CategoryDTO;
import com.esgworks.dto.UnitDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class AddUnitService{
    public static List<UnitDTO> parseCsv(InputStream is) {
        try {
            CSVFormat format = CSVFormat.Builder
                    .create(CSVFormat.DEFAULT)
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .build();

            CSVParser parser = new CSVParser(new InputStreamReader(is), format);
            List<UnitDTO> products = new ArrayList<>();
            for (CSVRecord record : parser) {
                UnitDTO u = new UnitDTO();
                u.setUnitId(record.get("unit_id"));
                u.setType(record.get("type"));
                u.setUnitName(record.get("unit_name"));

                products.add(u);
            }
            return products;
        } catch (Exception e) {
            throw new RuntimeException("CSV 파싱 실패: " + e.getMessage());
        }
    }
}
