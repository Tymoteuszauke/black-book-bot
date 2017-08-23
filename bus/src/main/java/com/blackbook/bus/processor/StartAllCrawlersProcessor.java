package com.blackbook.bus.processor;

import com.blackbook.bus.controller.core.RequestControllerListener;
import com.blackbook.bus.processor.core.AbstractProcessor;
import com.blackbook.bus.processor.core.RequestProcessor;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * @author Siarhei Shauchenka
 * @since 23.08.17
 */
public class StartAllCrawlersProcessor extends AbstractProcessor {

    private final String START_ALL_CRAWLERS_URL = "localhost:8080/api/crawlers/start";
    private final String OK_MESSAGE = "Crawlers started!";

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
