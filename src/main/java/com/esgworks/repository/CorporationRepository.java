package com.esgworks.repository;

import com.esgworks.domain.Corporation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorporationRepository extends MongoRepository<Corporation, String> {

}
