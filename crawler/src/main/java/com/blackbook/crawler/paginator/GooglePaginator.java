package com.blackbook.crawler.paginator;

import com.blackbook.crawler.paginator.core.Paginator;
import org.json.JSONObject;

/**
 * @author Siarhei Shauchenka
 * @since 17.08.17
 */
public class GooglePaginator implements Paginator{

    private final int NUMBER_BOOKS_ON_PAGE = 20;

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
