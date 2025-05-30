package com.esgworks.repository;

import com.esgworks.domain.Corporation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CorporationRepository extends MongoRepository<Corporation, String> {
    Optional<Corporation> findByCorpId(String corpId);
}
