package com.esgworks.repository;

import com.esgworks.domain.ESGData;
import com.esgworks.domain.InterestChart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterestChartRepository extends MongoRepository<InterestChart, String> {

    Optional<InterestChart> findByInterestChartId(String interestChartId);

    Optional<InterestChart> findByChartId(String chartId);
    // 특정 기업의 ESG 데이터를 조회
    List<InterestChart> findByUserId(String userId);

    boolean existsByUserIdAndChartId(String userId, String chartId);

}
