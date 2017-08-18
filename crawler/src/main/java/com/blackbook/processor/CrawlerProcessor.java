package com.blackbook.processor;

/**
 * @author Siarhei Shauchenka
 * @since 17.08.17
 */
public interface CrawlerProcessor {

    public static final int OK_STATUS = 200;

    void process(CrawlerProcessorListener actionListener);
}
