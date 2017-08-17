package com.blackbook.processor;

import com.blackbook.crowler.core.CrawlerActionListener;

/**
 * @author Siarhei Shauchenka
 * @since 17.08.17
 */
public interface CrawlerProcessor {

    void process(CrawlerProcessorListener actionListener);
    void initProcessor(String request);
}
