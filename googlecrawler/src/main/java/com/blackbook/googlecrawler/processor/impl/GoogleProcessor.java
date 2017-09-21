package com.blackbook.googlecrawler.processor.impl;

import com.blackbook.googlecrawler.processor.ResultModel;
import com.blackbook.googlecrawler.processor.core.AbstractProcessor;
import com.blackbook.googlecrawler.processor.core.CrawlerProcessorListener;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

/**
 * @author Siarhei Shauchenka
 */
@Slf4j
public class GoogleProcessor extends AbstractProcessor {

    public GoogleProcessor(String request, CrawlerProcessorListener processorListener) {
        super(new JsonRequestCreator(request), processorListener);
    }

    @Override
    public void onSuccess(JSONObject responseBody) {
        getProcessorListener().success(() -> new ResultModel(getDataParser().parseBooks(responseBody)));
    }

}
