package com.example.CrawlBatch.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DomainCode {
    FMKOREA("FMKOREA"),
    ILBE("ILBE");
    private final String domainName;
}
