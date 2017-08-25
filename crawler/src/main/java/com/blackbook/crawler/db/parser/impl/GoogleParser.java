package com.blackbook.crawler.db.parser.impl;

import com.blackbook.crawler.db.model.BookCreationData;
import com.blackbook.crawler.db.parser.core.DataParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.blackbook.crawler.db.parser.impl.GoogleParser.GoogleParserKeywords.AUTHOR_KEY;
import static com.blackbook.crawler.db.parser.impl.GoogleParser.GoogleParserKeywords.BOOKS_ARRAY_KEY;
import static com.blackbook.crawler.db.parser.impl.GoogleParser.GoogleParserKeywords.IS_FOR_SALE_KEY;
import static com.blackbook.crawler.db.parser.impl.GoogleParser.GoogleParserKeywords.NOT_FOR_SALE;
import static com.blackbook.crawler.db.parser.impl.GoogleParser.GoogleParserKeywords.PRICE_KEY;
import static com.blackbook.crawler.db.parser.impl.GoogleParser.GoogleParserKeywords.RETAIL_PRICE;
import static com.blackbook.crawler.db.parser.impl.GoogleParser.GoogleParserKeywords.SALE_INFO;
import static com.blackbook.crawler.db.parser.impl.GoogleParser.GoogleParserKeywords.TITLE_KEY;
import static com.blackbook.crawler.db.parser.impl.GoogleParser.GoogleParserKeywords.VOLUME_INFO_KEY;

/**
 * @author Siarhei Shauchenka
 * @since 17.08.17
 */
public class GoogleParser implements DataParser<JSONObject> {

    private static class GoogleParserKeywords {
        private static final String BOOKS_ARRAY_KEY = "items";
        private static final String AUTHOR_KEY = "authors";
        private static final String TITLE_KEY = "title";
        private static final String PRICE_KEY = "amount";
        private static final String VOLUME_INFO_KEY = "volumeInfo";
        private static final String SALE_INFO = "saleInfo";
        private static final String RETAIL_PRICE = "retailPrice";
        private static final String IS_FOR_SALE_KEY = "saleability";
        private static final String NOT_FOR_SALE = "NOT_FOR_SALE";
    }

    @Override
    public BookCreationData parseBook(JSONObject objectToParse) {
        BookCreationData bookCreationData = new BookCreationData();
        if (objectToParse.has(GoogleParserKeywords.VOLUME_INFO_KEY)) {
            JSONObject volumeInfoObject = objectToParse.getJSONObject(GoogleParserKeywords.VOLUME_INFO_KEY);
            bookCreationData.setTitle(volumeInfoObject.getString(GoogleParserKeywords.TITLE_KEY));
            if (volumeInfoObject.has(GoogleParserKeywords.AUTHOR_KEY)){
                bookCreationData.setAuthor(volumeInfoObject.getJSONArray(GoogleParserKeywords.AUTHOR_KEY).toString());
            }
        }

        JSONObject saleInfoObject = objectToParse.getJSONObject(GoogleParserKeywords.SALE_INFO);
        JSONObject listPriceObject = saleInfoObject.getJSONObject(GoogleParserKeywords.RETAIL_PRICE);
        bookCreationData.setPrice(listPriceObject.getDouble(GoogleParserKeywords.PRICE_KEY));

        return bookCreationData;
    }

    @Override
    public List<BookCreationData> parseBooks(JSONObject objectToParse) {
        if (objectToParse.has(GoogleParserKeywords.BOOKS_ARRAY_KEY)) {
            List<BookCreationData> bookCreationDataList = new ArrayList<>();
            JSONArray booksArray = objectToParse.getJSONArray(GoogleParserKeywords.BOOKS_ARRAY_KEY);
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
        if (!bookObject.has(GoogleParserKeywords.SALE_INFO)) {
            return false;
        }
        JSONObject saleInfoObject = bookObject.getJSONObject(GoogleParserKeywords.SALE_INFO);
        return !saleInfoObject.getString(GoogleParserKeywords.IS_FOR_SALE_KEY).equals(GoogleParserKeywords.NOT_FOR_SALE)
                && saleInfoObject.has(GoogleParserKeywords.RETAIL_PRICE);
    }
}
