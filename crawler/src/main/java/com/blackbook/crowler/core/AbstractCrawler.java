package com.blackbook.crowler.core;


import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author Sergey Shevchenko
 * @since 16.08.2017
 */
public abstract class AbstractCrawler implements ICrawler{


    @Override
    public void start(CrawlerActionListener actionListener) {
        Future<HttpResponse<JsonNode>> future = Unirest.get(getRequest())
                .header("accept", "application/json")
                .asJsonAsync(new Callback<JsonNode>() {

                    public void failed(UnirestException e) {
                        System.out.println("The request has failed");
                    }

                    public void completed(HttpResponse<JsonNode> response) {

                        int code = response.getStatus();
                        Headers headers = response.getHeaders();
                        JsonNode body = response.getBody();
                        actionListener.crawlerFinished(getId(), body);
                    }

                    public void cancelled() {
                        System.out.println("The request has been cancelled");
                    }
                });
        actionListener.crawlerStarted(getId());
    }

}
