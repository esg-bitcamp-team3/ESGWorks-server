package com.esgworks.repository;

import com.esgworks.domain.Template;
import com.esgworks.domain.Unit;
import com.esgworks.dto.TemplateDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemplateRepository extends MongoRepository<Template, String> {
    Template findByTemplateId(String id);
}
