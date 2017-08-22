package com.blackbook.crawler.db.parser;

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
    private final String LIST_PRICE_KEY = "listPrice";

    @Override
    public BookCreationData parseBook(JSONObject objectToParse) {
        BookCreationData bookCreationData = new BookCreationData();
        if (objectToParse.has(VOLUME_INFO_KEY)) {
            JSONObject volumeInfoObject = objectToParse.getJSONObject(VOLUME_INFO_KEY);
            bookCreationData.setTitle(volumeInfoObject.getString(TITLE_KEY));
            bookCreationData.setAuthor(volumeInfoObject.getJSONArray(AUTHOR_KEY).toString());
        }
        if(objectToParse.has(SALE_INFO)){
            JSONObject saleInfoObject = objectToParse.getJSONObject(SALE_INFO);
            if (saleInfoObject.has(LIST_PRICE_KEY)){
                JSONObject listPriceObject = saleInfoObject.getJSONObject(LIST_PRICE_KEY);
                bookCreationData.setPrice(listPriceObject.getDouble(PRICE_KEY));
            }
        }
        return bookCreationData;
    }

    @Override
    public List<BookCreationData> parseBooks(JSONObject objectToParse) {
        if (objectToParse.has(BOOKS_ARRAY_KEY)) {
            List<BookCreationData> bookCreationDataList = new ArrayList<>();
            JSONArray booksArray = objectToParse.getJSONArray(BOOKS_ARRAY_KEY);
            for (int i = 0; i < booksArray.length(); i++) {
                JSONObject bookObject = booksArray.getJSONObject(i);
                bookCreationDataList.add(parseBook(bookObject));
            }
            return bookCreationDataList;
        }
        return Collections.emptyList();
    }
}
