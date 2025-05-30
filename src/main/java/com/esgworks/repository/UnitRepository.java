package com.esgworks.repository;

import com.esgworks.domain.Unit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitRepository extends MongoRepository<Unit, String> {

    // unitId로 조회 (Optional 반환)
    Optional<Unit> findByUnitId(String unitId);
}
