package com.blackbook.processor.impl;

import com.blackbook.db.parser.GoogleParser;
import com.blackbook.db.parser.core.DataParser;
import com.blackbook.processor.CrawlerProcessor;
import com.blackbook.processor.CrawlerProcessorListener;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
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

    public GoogleProcessor(String request, int currentPage) {
        this.request = request;
        this.dataParser = new GoogleParser();
        this.currentPage = currentPage;
    }

    @Override
    public void process(CrawlerProcessorListener processorListener) {
        Unirest.get(request)
                .header("accept", "application/json")
                .asJsonAsync(new Callback<JsonNode>() {

                    public void failed(UnirestException e) {
                        System.out.println("The request has failed");
                    }

                    public void completed(HttpResponse<JsonNode> response) {
                        int code = response.getStatus();
                        if (code == OK_STATUS) {
                            JSONObject responseBody = response.getBody().getObject();

                        //    processorListener.success(dataParser.parseBooks(responseBody), new GooglePaginator(responseBody, currentPage));
                        } else {
                       //     processorListener.failed(response.getStatusText());
                        }
                    }

                    public void cancelled() {
                        System.out.println("The request has been cancelled");
                    }
                });
    }

}
