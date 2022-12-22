package com.example.CrawlBatch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="crawl_result")
@Builder
public class CrawlResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int crawlResultId;
    private String title;
    private String body;
    private String url;
    private String ymd;

    @Enumerated(value=EnumType.STRING)
    private DomainCode domain;

    @Enumerated(value=EnumType.STRING)
    private CategoryCode category;

}
