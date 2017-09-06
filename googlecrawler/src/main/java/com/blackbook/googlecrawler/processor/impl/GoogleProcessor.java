package com.blackbook.googlecrawler.processor.impl;

import com.blackbook.googlecrawler.paginator.impl.GooglePaginator;
import com.blackbook.googlecrawler.parser.core.DataParser;
import com.blackbook.googlecrawler.parser.impl.GoogleParser;
import com.blackbook.googlecrawler.processor.core.CrawlerProcessor;
import com.blackbook.googlecrawler.processor.core.CrawlerProcessorListener;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

/**
 * @author Siarhei Shauchenka
 * @since 17.08.17
 */
@Slf4j
public class GoogleProcessor implements CrawlerProcessor {
    private static final int OK_STATUS = 200;
    private final String request;
    private final DataParser<JSONObject> dataParser;
    private final int currentPage;
    private final CrawlerProcessorListener processorListener;

    public GoogleProcessor(String request, int currentPage, CrawlerProcessorListener processorListener) {
        this.request = request;
        this.dataParser = new GoogleParser();
        this.currentPage = currentPage;
        this.processorListener = processorListener;
    }

    @Override
    public void run() {
        try {
            log.info(request);
            HttpResponse<JsonNode> jsonResponse = Unirest.get(request)
                    .header("accept", "application/json")
                    .asJson();
            int code = jsonResponse.getStatus();
            if (code == OK_STATUS) {
                JSONObject responseBody = jsonResponse.getBody().getObject();
                //log.info(responseBody.toString());
                processorListener.success(dataParser.parseBooks(responseBody), new GooglePaginator(responseBody, currentPage));
            } else {
                processorListener.failed(jsonResponse.getStatusText());
            }
        } catch (UnirestException e) {
            processorListener.failed(e.getMessage());
        }
    }
}
