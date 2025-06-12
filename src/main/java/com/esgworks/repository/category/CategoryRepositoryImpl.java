package com.esgworks.repository.category;

import com.esgworks.domain.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

  private final MongoTemplate mongoTemplate;

  @Override
  public List<Category> search(String keyword) {
    Query query = new Query();

    // 검색 조건 구성
    if (keyword != null && !keyword.isEmpty()) {
      query.addCriteria(Criteria.where("categoryName").regex(keyword, "i")); // 이름 필드에 keyword 검색 (대소문자 무시)
    }

//    if (filter != null && !filter.isEmpty()) {
//      query.addCriteria(Criteria.where("filterField").is(filter)); // 예: "type" 같은 필터 필드
//    }
//
//    if (userId != null && !userId.isEmpty()) {
//      query.addCriteria(Criteria.where("userId").is(userId));
//    }

    return mongoTemplate.find(query, Category.class);
  }
}
