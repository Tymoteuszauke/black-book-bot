package com.blackbook.scheduler.processor;

import com.blackbook.scheduler.controller.core.RequestControllerListener;
import com.blackbook.scheduler.processor.core.AbstractProcessor;

/**
 * @author Siarhei Shauchenka
 * @since 23.08.17
 */
public class StartAllCrawlersProcessor extends AbstractProcessor {

    private static final String START_ALL_CRAWLERS_URL = "localhost:8080/api/crawlers/start";
    private static final String OK_MESSAGE = "Crawlers started!";

    public StartAllCrawlersProcessor(RequestControllerListener controllerListener) {
        super(controllerListener);
    }

    @Override
    public String getProcessorURL() {
        return START_ALL_CRAWLERS_URL;
    }

    @Override
    public String getOkMessage() {
        return OK_MESSAGE;
    }
}
