package com.blackbook.crawler.processor.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;

import static com.blackbook.crawler.processor.core.CrawlerProcessor.OK_STATUS;

/**
 * @author Siarhei Shauchenka
 * @since 24.08.17
 */
@Slf4j
public class RestRequestProcessor implements Runnable{

    private final String url;

    public RestRequestProcessor(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.post(url)
                    .header("accept", "application/json")
                    .asJson();
            int code = jsonResponse.getStatus();
            if (code == OK_STATUS){
                log.info("Request was sent!");
            }

        } catch (UnirestException e) {
            log.error("Request sending failed. Reason is:" + e.getMessage());
        }
    }
}
