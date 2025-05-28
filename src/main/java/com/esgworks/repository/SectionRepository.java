package com.esgworks.repository;

import com.esgworks.domain.Section;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepository extends MongoRepository<Section, String> {
    List<Section> findByCriterionId(String criterionId);
    Optional<Section> findBySectionId(String sectionId);
}
