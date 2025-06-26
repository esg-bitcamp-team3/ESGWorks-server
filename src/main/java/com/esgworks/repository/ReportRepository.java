package com.esgworks.repository;

import com.esgworks.domain.Report;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends MongoRepository<Report, String>, ReportRepositoryCustom {
//    List<Report> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);
    List<Report> search(String keyword, String filter, String userId);
    Report findByTitle(String title);
}
