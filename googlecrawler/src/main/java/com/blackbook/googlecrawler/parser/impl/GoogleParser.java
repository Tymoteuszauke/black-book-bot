package com.blackbook.googlecrawler.parser.impl;


import com.blackbook.googlecrawler.impl.GoogleCrawler;
import com.blackbook.googlecrawler.parser.core.DataParser;
import com.blackbook.utils.view.creationmodel.BookData;
import com.blackbook.utils.view.creationmodel.BookDiscountData;
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

    private static class GoogleParserKeywords {
        private static final String BOOKS_ARRAY_KEY = "items";
        private static final String BOOK_PAGE_URL_KEY = "selfLink";
        private static final String AUTHOR_KEY = "authors";
        private static final String IMAGES_LINK_KEY = "imageLinks";
        private static final String COVER_URL_KEY = "thumbnail";
        private static final String TITLE_KEY = "title";
        private static final String SUBTITLE_KEY = "subtitle";
        private static final String CATEGORIES_KEY = "categories";
        private static final String VOLUME_INFO_KEY = "volumeInfo";
        private static final String SALE_INFO = "saleInfo";
        private static final String LIST_PRICE = "listPrice";
        private static final String RETAIL_PRICE = "retailPrice";
        private static final String PRICE_KEY = "amount";
        private static final String IS_FOR_SALE_KEY = "saleability";
        private static final String NOT_FOR_SALE = "NOT_FOR_SALE";
    }

    @Override
    public BookDiscountData parseBook(JSONObject objectToParse) {
        BookDiscountData.BookDiscountDataBuilder bookDiscountDataBuilder = BookDiscountData.builder();
        BookData.BookDataBuilder bookDataBuilder = BookData.builder();

        bookDataBuilder.bookPageUrl(objectToParse.getString(GoogleParserKeywords.BOOK_PAGE_URL_KEY));

        if (objectToParse.has(GoogleParserKeywords.VOLUME_INFO_KEY)) {
            JSONObject volumeInfoObject = objectToParse.getJSONObject(GoogleParserKeywords.VOLUME_INFO_KEY);
            bookDataBuilder.title(volumeInfoObject.getString(GoogleParserKeywords.TITLE_KEY));

            if (volumeInfoObject.has(GoogleParserKeywords.SUBTITLE_KEY)){
                bookDataBuilder.subtitle(volumeInfoObject.getString(GoogleParserKeywords.SUBTITLE_KEY));
            }

            if (volumeInfoObject.has(GoogleParserKeywords.CATEGORIES_KEY)){
                String categoriesArrayString = volumeInfoObject.getJSONArray(GoogleParserKeywords.CATEGORIES_KEY).toString();
                bookDataBuilder.genre(categoriesArrayString.substring(1, categoriesArrayString.length()-1));
            }

            if (volumeInfoObject.has(GoogleParserKeywords.AUTHOR_KEY)){
                String authorsArrayString = volumeInfoObject.getJSONArray(GoogleParserKeywords.AUTHOR_KEY).toString();
                bookDataBuilder.authors(authorsArrayString.substring(1, authorsArrayString.length()-1));
            }

            if (volumeInfoObject.has(GoogleParserKeywords.IMAGES_LINK_KEY)){
                JSONObject imagesObject = volumeInfoObject.getJSONObject(GoogleParserKeywords.IMAGES_LINK_KEY);
                bookDataBuilder.coverUrl(imagesObject.getString(GoogleParserKeywords.COVER_URL_KEY));
            }
        }

        bookDiscountDataBuilder.bookData(bookDataBuilder.build());

        JSONObject saleInfoObject = objectToParse.getJSONObject(GoogleParserKeywords.SALE_INFO);

        JSONObject listPriceObject = saleInfoObject.getJSONObject(GoogleParserKeywords.LIST_PRICE);
        double listPrice = listPriceObject.getDouble(GoogleParserKeywords.PRICE_KEY);

        JSONObject retailPriceObject = saleInfoObject.getJSONObject(GoogleParserKeywords.RETAIL_PRICE);
        double retailPrice = retailPriceObject.getDouble(GoogleParserKeywords.PRICE_KEY);

        bookDiscountDataBuilder.price(retailPrice);

        double discountInfo = 100 -retailPrice/listPrice*100;

        bookDiscountDataBuilder.bookDiscountDetails(String.format("%1$,.2f%%", discountInfo));

        bookDiscountDataBuilder.bookstoreId(GoogleCrawler.GOOGLE_CRAWLER_ID);

        return bookDiscountDataBuilder.build();
    }

    @Override
    public List<BookDiscountData> parseBooks(JSONObject objectToParse) {
        if (objectToParse.has(GoogleParserKeywords.BOOKS_ARRAY_KEY)) {
            List<BookDiscountData> bookCreationDataList = new ArrayList<>();
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
