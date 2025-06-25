package com.esgworks.repository;

import com.esgworks.domain.Chart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChartRepository extends MongoRepository<Chart, String> {

    // 특정 기업의 ESG 데이터를 조회
    Optional<Chart> findByChartId(String chartId);

    // 특정 기업의 ESG 데이터를 더미로 조회
    List<Chart> findAllByCorporationIdOrderByCreatedAtDesc(String corporationId);

    Optional<Chart> findByCorporationIdAndChartName(String corporationId, String chartName);


}
