package com.blackbook.googlecrawler.processor.core;

import com.blackbook.googlecrawler.parser.core.DataParser;
import com.blackbook.googlecrawler.parser.impl.GoogleParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.json.JSONObject;

/**
 * @author Siarhei Shauchenka at 07.09.17
 */
@Slf4j
public abstract class AbstractProcessor implements CrawlerProcessor, ResponseListener {

    private final String request;
    private final DataParser<JSONObject> dataParser;
    private final CrawlerProcessorListener processorListener;

    public AbstractProcessor(String request, CrawlerProcessorListener processorListener) {
        this.request = request;
        this.dataParser = new GoogleParser();
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
            if (code == HttpStatus.SC_OK) {
                JSONObject responseBody = jsonResponse.getBody().getObject();
                onSuccess(responseBody);
            } else {
                onFailed(jsonResponse.getStatusText());
            }
        } catch (UnirestException e) {
            onFailed(e.getMessage());
        }
    }

    @Override
    public void onFailed(String errorMessage) {
        getProcessorListener().failed(errorMessage);
    }

    protected DataParser<JSONObject> getDataParser() {
        return dataParser;
    }

    protected CrawlerProcessorListener getProcessorListener() {
        return processorListener;
    }
}
