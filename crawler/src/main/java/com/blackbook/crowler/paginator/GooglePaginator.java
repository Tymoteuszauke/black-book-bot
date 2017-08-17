package com.blackbook.crowler.paginator;

import com.blackbook.crowler.paginator.core.Paginator;
import org.json.JSONObject;

/**
 * @author Siarhei Shauchenka
 * @since 17.08.17
 */
public class GooglePaginator implements Paginator{

    private final JSONObject data;

    public GooglePaginator(JSONObject jsonObject) {
        data = jsonObject;
    }

    @Override
    public int getTotalPage() {
        return 1;
    }

    @Override
    public int getCurrentPage() {
        return 1;
    }
}
