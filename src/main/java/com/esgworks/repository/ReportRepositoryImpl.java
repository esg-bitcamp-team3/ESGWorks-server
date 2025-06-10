package com.esgworks.repository;

import com.esgworks.domain.Report;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReportRepositoryImpl implements ReportRepositoryCustom{
    private final MongoTemplate mongoTemplate;

    @Override
    public List<Report> search(String keyword, String filter, String userId) {
        Query query = new Query();
        if(keyword != null && !keyword.isEmpty()) {
            query.addCriteria(new Criteria().orOperator(
            Criteria.where("title").regex(keyword,"i"),
                    Criteria.where("content").regex(keyword,"i")
            ));
        }
        if("recent".equals(filter)) {
            query.with(org.springframework.data.domain.Sort.by(Sort.Direction.DESC, "updatedAt"));
        }
        if("favorite".equals(filter) && userId != null) {
            query.addCriteria(Criteria.where("favoriteUserIds").in(userId));
        }

        return mongoTemplate.find(query, Report.class);
    }
}
