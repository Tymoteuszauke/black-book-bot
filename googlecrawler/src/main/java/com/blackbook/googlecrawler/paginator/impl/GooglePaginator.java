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

    public GooglePaginator(JSONObject jsonObject){
        data = jsonObject;
    }

    @Override
    public int getNumberOfPages() {
        return getTotalNumberOfItems()/NUMBER_BOOKS_ON_PAGE;
    }

    @Override
    public int getItemsOnPage() {
        return NUMBER_BOOKS_ON_PAGE;
    }

    @Override
    public int getTotalNumberOfItems() {
        return data.getInt("totalItems");
    }
}
