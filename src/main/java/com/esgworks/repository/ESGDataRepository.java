package com.esgworks.repository;

import com.esgworks.domain.ESGData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ESGDataRepository extends MongoRepository<ESGData, String> {

    Optional<ESGData> findById(String esgDataId);
    // 특정 기업의 ESG 데이터를 조회
    List<ESGData> findByCorpId(String corpId);

    // 특정 기업의 ESG 데이터를 더미로 조회
    List<ESGData> findAllByCorpIdAndYear(String corpId, String year);

    // 기업 ID와 연도를 기준으로 ESG 데이터 조회
    Optional<ESGData> findByCorpIdAndYear(String corpId, String year);
}