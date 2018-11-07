package com.loyaltyplant.testassignment.crawler;

public interface Crawler<T> {
    CrawlerProcess<T> submitJob(int offset, int limit);
}
