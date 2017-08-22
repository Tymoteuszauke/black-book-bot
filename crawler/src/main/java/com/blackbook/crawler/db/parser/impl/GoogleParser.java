package com.blackbook.crawler.db.parser.impl;

import com.blackbook.crawler.db.model.BookCreationData;
import com.blackbook.crawler.db.parser.core.DataParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Siarhei Shauchenka
 * @since 17.08.17
 */
public class GoogleParser implements DataParser<JSONObject> {

    private final String BOOKS_ARRAY_KEY = "items";
    private final String AUTHOR_KEY = "authors";
    private final String TITLE_KEY = "title";
    private final String PRICE_KEY = "amount";
    private final String VOLUME_INFO_KEY = "volumeInfo";
    private final String SALE_INFO = "saleInfo";
    private final String RETAIL_PRICE = "retailPrice";
    private final String IS_FOR_SALE_KEY = "saleability";
    private final String NOT_FOR_SALE = "NOT_FOR_SALE";

    @Override
    public BookCreationData parseBook(JSONObject objectToParse) {
        BookCreationData bookCreationData = new BookCreationData();
        if (objectToParse.has(VOLUME_INFO_KEY)) {
            JSONObject volumeInfoObject = objectToParse.getJSONObject(VOLUME_INFO_KEY);
            bookCreationData.setTitle(volumeInfoObject.getString(TITLE_KEY));
            if (volumeInfoObject.has(AUTHOR_KEY)){
                bookCreationData.setAuthor(volumeInfoObject.getJSONArray(AUTHOR_KEY).toString());
            }
        }

        JSONObject saleInfoObject = objectToParse.getJSONObject(SALE_INFO);
        JSONObject listPriceObject = saleInfoObject.getJSONObject(RETAIL_PRICE);
        bookCreationData.setPrice(listPriceObject.getDouble(PRICE_KEY));

        return bookCreationData;
    }

    @Override
    public List<BookCreationData> parseBooks(JSONObject objectToParse) {
        if (objectToParse.has(BOOKS_ARRAY_KEY)) {
            List<BookCreationData> bookCreationDataList = new ArrayList<>();
            JSONArray booksArray = objectToParse.getJSONArray(BOOKS_ARRAY_KEY);
            for (int i = 0; i < booksArray.length(); i++) {
                JSONObject bookObject = booksArray.getJSONObject(i);
                if (isForSaleAndRetail(bookObject)) {
                    bookCreationDataList.add(parseBook(bookObject));
                }
            }
            return bookCreationDataList;
        }
        return Collections.emptyList();
    }

    private boolean isForSaleAndRetail(JSONObject bookObject) {
        if (!bookObject.has(SALE_INFO)) {
            return false;
        }
        JSONObject saleInfoObject = bookObject.getJSONObject(SALE_INFO);
        return !saleInfoObject.getString(IS_FOR_SALE_KEY).equals(NOT_FOR_SALE) && saleInfoObject.has(RETAIL_PRICE);
    }
}
