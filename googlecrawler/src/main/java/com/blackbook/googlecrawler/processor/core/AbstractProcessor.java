package com.blackbook.googlecrawler.processor.core;

import com.blackbook.googlecrawler.parser.core.DataParser;
import com.blackbook.googlecrawler.parser.impl.GoogleParser;
import com.blackbook.googlecrawler.processor.impl.JsonRequestCreator;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

/**
 * @author Siarhei Shauchenka at 07.09.17
 */
@Slf4j
public abstract class AbstractProcessor implements CrawlerProcessor, ResponseListener {

    private final JsonRequestCreator requestCreator;
    private final DataParser<JSONObject> dataParser;
    private final CrawlerProcessorListener processorListener;

    public AbstractProcessor(JsonRequestCreator requestCreator, CrawlerProcessorListener processorListener) {
        this.requestCreator = requestCreator;
        this.dataParser = new GoogleParser();
        this.processorListener = processorListener;
    }

    @Override
    public void run() {
        requestCreator.makeRequest();
        if (requestCreator.isSuccess()){
            onSuccess(requestCreator.getResponseBody());
        } else {
            onFailed(requestCreator.getErrorMessage());
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
