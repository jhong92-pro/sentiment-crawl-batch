package com.example.CrawlBatch.dao;

import com.example.CrawlBatch.model.CrawlResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

@Repository
public interface CrawlResultDao extends JpaRepository<CrawlResult, Integer> {
//    void saveAll(List<CrawlResult> crawlResultList);
    boolean existsByUrl(String url);
}
