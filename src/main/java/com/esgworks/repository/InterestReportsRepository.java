package com.esgworks.repository;


import com.esgworks.domain.InterestReports;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface InterestReportsRepository extends MongoRepository<InterestReports, String> {
  List<InterestReports> findByUserId(String userId);
}
