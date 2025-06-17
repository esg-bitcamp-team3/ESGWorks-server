package com.esgworks.repository;

import com.esgworks.domain.Report;
import java.util.List;

public interface ReportRepositoryCustom {
    List<Report> search(String keyword, String filter, String userId);
}
