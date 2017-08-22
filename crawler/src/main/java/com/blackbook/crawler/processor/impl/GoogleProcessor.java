package com.blackbook.crawler.processor.impl;

import com.blackbook.crawler.db.parser.impl.GoogleParser;
import com.blackbook.crawler.db.parser.core.DataParser;
import com.blackbook.crawler.paginator.impl.GooglePaginator;
import com.blackbook.crawler.processor.core.CrawlerProcessor;
import com.blackbook.crawler.processor.core.CrawlerProcessorListener;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

/**
 * @author Siarhei Shauchenka
 * @since 17.08.17
 */
public class GoogleProcessor implements CrawlerProcessor {

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
            HttpResponse<JsonNode> jsonResponse = Unirest.get(request)
                    .header("accept", "application/json")
                    .asJson();
            int code = jsonResponse.getStatus();
            if (code == OK_STATUS) {
                JSONObject responseBody = jsonResponse.getBody().getObject();
                processorListener.success(dataParser.parseBooks(responseBody), new GooglePaginator(responseBody, currentPage));
            } else {
                processorListener.failed(jsonResponse.getStatusText());
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }
}
