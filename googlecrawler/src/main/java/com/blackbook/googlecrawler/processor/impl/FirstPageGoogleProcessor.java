package com.blackbook.googlecrawler.processor.impl;

import com.blackbook.googlecrawler.paginator.impl.GooglePaginator;
import com.blackbook.googlecrawler.processor.ResultModel;
import com.blackbook.googlecrawler.processor.core.AbstractProcessor;
import com.blackbook.googlecrawler.processor.core.CrawlerProcessorListener;
import org.json.JSONObject;

/**
 * @author Siarhei Shauchenka at 07.09.17
 */
public class FirstPageGoogleProcessor extends AbstractProcessor {


    public FirstPageGoogleProcessor(String request, CrawlerProcessorListener processorListener) {
        super(request, processorListener);
    }

    @Override
    public void onSuccess(JSONObject responseBody) {
        getProcessorListener().success(() -> new ResultModel(getDataParser().parseBooks(responseBody), new GooglePaginator(responseBody)));
    }

}
