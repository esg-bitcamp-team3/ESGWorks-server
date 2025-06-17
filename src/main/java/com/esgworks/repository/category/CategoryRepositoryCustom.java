package com.esgworks.repository.category;

import com.esgworks.domain.Category;

import java.util.List;

public interface CategoryRepositoryCustom {
  List<Category> search(String keyword);
}