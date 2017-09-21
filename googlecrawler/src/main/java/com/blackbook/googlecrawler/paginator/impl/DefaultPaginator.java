package com.blackbook.googlecrawler.paginator.impl;

import com.blackbook.googlecrawler.paginator.core.Paginator;

/**
 * @author Siarhei Shauchenka at 07.09.17
 */
public class DefaultPaginator implements Paginator{

    private static final int DEFAULT_NUMBER_OF_PAGES = 1;
    private static final int DEFAULT_ITEMS_ON_PAGE = 20;

    @Override
    public int getNumberOfPages() {
        return DEFAULT_NUMBER_OF_PAGES;
    }

    @Override
    public int getItemsOnPage() {
        return DEFAULT_ITEMS_ON_PAGE;
    }

    @Override
    public int getTotalNumberOfItems() {
        return DEFAULT_ITEMS_ON_PAGE;
    }
}
