package com.blackbook.crowler.impl;

import com.blackbook.crowler.core.AbstractCrawler;
import com.blackbook.crowler.core.CrawlerActionListener;
import com.blackbook.crowler.core.KeyAccess;

/**
 * @author Sergey Shevchenko
 * @since 16.08.2017
 */
public class ISBNdbCrawler extends AbstractCrawler implements KeyAccess{

    private final String BASE_URL = "http://isbndb.com/api/v2/json";
    private final String CRITERIA = "books?q=science";

    private final String devKey;

    public ISBNdbCrawler(String devKey) {
        this.devKey = devKey;
    }

    @Override
    public void finish(CrawlerActionListener actionListener) {

    }

    @Override
    public String getId() {
        return ISBNdbCrawler.class.getSimpleName();
    }

    @Override
    public String getBaseUrl() {
        return BASE_URL;
    }

    @Override
    public String getRequest() {
        StringBuilder builder = new StringBuilder();
        builder.append(getBaseUrl()).append("/");
        builder.append(getKey()).append("/");
        builder.append(getCriteria());
        return builder.toString();
    }

    @Override
    public String getCriteria() {
        return CRITERIA;
    }

    @Override
    public String getKey() {
        return devKey;
    }
}
