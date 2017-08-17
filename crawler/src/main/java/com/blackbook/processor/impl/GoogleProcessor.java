package com.blackbook.processor.impl;

import com.blackbook.crowler.paginator.GooglePaginator;
import com.blackbook.crowler.paginator.ISBNPaginator;
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
public class GoogleProcessor implements CrawlerProcessor{

    private String request;
    private boolean isInitialized = false;
    private final DataParser<JSONObject> dataParser;

    public GoogleProcessor() {
        this.dataParser = new GoogleParser();
    }

    @Override
    public void process(CrawlerProcessorListener actionListener) {
        if (isInitialized){
            Unirest.get(request)
                    .header("accept", "application/json")
                    .asJsonAsync(new Callback<JsonNode>() {

                        public void failed(UnirestException e) {
                            System.out.println("The request has failed");
                        }

                        public void completed(HttpResponse<JsonNode> response) {
                            int code = response.getStatus();
                            if (code == OK_STATUS){
                                JSONObject responseBody = response.getBody().getObject();

                                actionListener.success(dataParser.parseBooks(responseBody), new GooglePaginator(responseBody));
                            } else {
                                actionListener.failed(response.getStatusText());
                            }
                        }

                        public void cancelled() {
                            System.out.println("The request has been cancelled");
                        }
                    });
        }
    }

    @Override
    public void initProcessor(String request) {
        this.request = request;
        isInitialized = true;
    }
}
