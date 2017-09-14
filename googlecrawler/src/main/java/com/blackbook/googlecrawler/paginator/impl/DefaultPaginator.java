package com.blackbook.googlecrawler.paginator.impl;

import com.blackbook.googlecrawler.paginator.core.Paginator;

/**
 * @author Siarhei Shauchenka at 07.09.17
 */
public class DefaultPaginator implements Paginator{

    private static final int defaultNumberOfPages = 1;
    private static final int defaultItemsOnPage = 20;

    @Override
    public int getNumberOfPages() {
        return defaultNumberOfPages;
    }

    @Override
    public int getItemsOnPage() {
        return defaultItemsOnPage;
    }

    @Override
    public int getTotalNumberOfItems() {
        return defaultItemsOnPage;
    }
}
