package com.blackbook.googlecrawler.paginator.impl;

import com.blackbook.googlecrawler.paginator.core.Paginator;
import org.json.JSONObject;

/**
 * @author Siarhei Shauchenka
 * @since 17.08.17
 */
public class GooglePaginator implements Paginator{

    public static final int NUMBER_BOOKS_ON_PAGE = 40;

    private final JSONObject data;
    private final int currentPage;

    public GooglePaginator(JSONObject jsonObject, int currentPage) {
        this.currentPage = currentPage;
        data = jsonObject;
    }

    @Override
    public int getNumberOfPages() {
        return data.getInt("totalItems")/NUMBER_BOOKS_ON_PAGE;
    }

    @Override
    public int getItemsOnPage() {
        return NUMBER_BOOKS_ON_PAGE;
    }

    @Override
    public int getCurrentPage() {
        return currentPage;
    }
}
