package com.esgworks.repository;

import com.esgworks.domain.Criterion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CriterionRepository extends MongoRepository<Criterion, String> {
    Optional<Criterion> findByCriterionId(String criterionId);
    @Query("{ '$or': [ { 'corporationId': ?0 }, { 'corporationId': null } ] }")
    List<Criterion> findByCorporationIdIncludingNull(String corporationId);
}
