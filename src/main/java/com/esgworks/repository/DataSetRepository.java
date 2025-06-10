package com.esgworks.repository;

import com.esgworks.domain.DataSet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DataSetRepository extends MongoRepository<DataSet, String> {

    // ID로 단건 조회
    Optional<DataSet> findDataSetByDataSetId(String dataSetId);

    // 특정 차트(chartId)에 연결된 데이터셋 전부 조회
    List<DataSet> findAllByChartId(String chartId);

    List<DataSet> findAllByChartIdAndType(String chartId, String type);
}
