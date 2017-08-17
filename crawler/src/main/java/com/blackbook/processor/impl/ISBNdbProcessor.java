package com.blackbook.processor.impl;

import com.blackbook.crowler.core.CrawlerActionListener;
import com.blackbook.crowler.paginator.ISBNPaginator;
import com.blackbook.crowler.paginator.core.Paginator;
import com.blackbook.processor.CrawlerProcessor;
import com.blackbook.processor.CrawlerProcessorListener;
import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;


/**
 * @author Siarhei Shauchenka
 * @since 17.08.17
 */
public class ISBNdbProcessor implements CrawlerProcessor{

    private final int OK = 200;

    private String request;
    private boolean isInitialized = false;

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
                            if (code == OK){
                                JSONObject responseBody = response.getBody().getObject();
                                actionListener.success(responseBody, new ISBNPaginator(responseBody));
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
        this.isInitialized = true;
    }

}
