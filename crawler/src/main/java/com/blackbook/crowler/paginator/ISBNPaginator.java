package com.blackbook.crowler.paginator;

import com.blackbook.crowler.paginator.core.Paginator;
import org.json.JSONObject;

/**
 * @author Siarhei Shauchenka
 * @since 17.08.17
 */
public class ISBNPaginator implements Paginator{

    private final String TOTAL_PAGE_KEY = "page_count";
    private final String CURRENT_PAGE_KEY = "current_page";

    private final int totalPage;
    private final int currentPage;

    public ISBNPaginator(JSONObject jsonObject) {
        totalPage = jsonObject.getInt(TOTAL_PAGE_KEY);
        currentPage = jsonObject.getInt(CURRENT_PAGE_KEY);
    }

    @Override
    public int getTotalPage() {
        return totalPage;
    }

    @Override
    public int getCurrentPage() {
        return currentPage;
    }
}
