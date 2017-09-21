package com.blackbook.googlecrawler.processor.core;


import com.blackbook.googlecrawler.processor.ResultModel;

import java.util.function.Supplier;

/**
 * @author Siarhei Shauchenka
 * @since 17.08.17
 */
public interface CrawlerProcessorListener {
    void success(Supplier<ResultModel> resultSupplier);
    void failed(String message);
}
