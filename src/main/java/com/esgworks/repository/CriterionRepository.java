package com.esgworks.repository;

import com.esgworks.domain.Criterion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CriterionRepository extends MongoRepository<Criterion, String> {
    Optional<Criterion> findByCriterionId(String criterionId);
}
