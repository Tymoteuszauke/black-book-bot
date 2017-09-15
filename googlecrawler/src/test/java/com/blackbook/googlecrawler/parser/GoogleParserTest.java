package com.blackbook.googlecrawler.parser;

import com.blackbook.googlecrawler.parser.core.DataParser;
import com.blackbook.googlecrawler.parser.impl.GoogleParser;
import com.blackbook.utils.view.creationmodel.BookData;
import com.blackbook.utils.view.creationmodel.BookDiscountData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author Siarhei Shauchenka at 07.09.17
 */
@Test
public class GoogleParserTest {

    private static final int GOOGLE_CRAWLER_ID = 4;
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
    private static final String FOR_SALE = "FOR_SALE";
    private static final String NOT_FOR_SALE = "NOT_FOR_SALE";

    private static final String TEST_BOOK_PAGE_URL = "book page url";
    private static final String TEST_IMAGE_URL = "image url";
    private static final String TEST_TITLE = "test title";
    private static final String TEST_SUBTITLE = "test subtitle";
    private static final String[] TEST_CATEGORIES_RAW = {"category 1", "category 2"};
    private static final String TEST_CATEGORIES_RESULT = "\"category 1\",\"category 2\"";
    private static final String[] TEST_AUTHORS_RAW = {"author 1", "author 2"};
    private static final String TEST_AUTHORS_RESULT = "\"author 1\",\"author 2\"";

    private static final double TEST_LIST_PRISE = 10.00;
    private static final double TEST_RETAIL_PRISE = 8.00;
    private static final String TEST_DISCOUNT_RESULT = "20.00%";


    private DataParser<JSONObject> parser;
    private JSONObject oneSuccessBookFakeObject;
    private JSONObject oneNotSuccessBookFakeObject;


    @BeforeClass
    private void prepareParser() {
        parser = new GoogleParser();
    }

    @BeforeClass
    private void prepareSuccessFakeData() throws JSONException {
        oneSuccessBookFakeObject = new JSONObject();
        oneSuccessBookFakeObject.put(BOOK_PAGE_URL_KEY, TEST_BOOK_PAGE_URL);

        JSONObject volumeInfoObject = new JSONObject();
        volumeInfoObject.put(TITLE_KEY, TEST_TITLE);
        volumeInfoObject.put(SUBTITLE_KEY, TEST_SUBTITLE);

        JSONArray categories = new JSONArray(TEST_CATEGORIES_RAW);

        volumeInfoObject.put(CATEGORIES_KEY, categories);

        JSONArray authors = new JSONArray(TEST_AUTHORS_RAW);

        volumeInfoObject.put(AUTHOR_KEY, authors);

        JSONObject imagesJsonObject = new JSONObject();
        imagesJsonObject.put(COVER_URL_KEY, TEST_IMAGE_URL);

        volumeInfoObject.put(IMAGES_LINK_KEY, imagesJsonObject);

        oneSuccessBookFakeObject.put(VOLUME_INFO_KEY, volumeInfoObject);

        JSONObject saleInfoObject = new JSONObject();
        saleInfoObject.put(IS_FOR_SALE_KEY, FOR_SALE);

        JSONObject listPriseObject = new JSONObject();
        JSONObject retailPriseObject = new JSONObject();

        listPriseObject.put(PRICE_KEY, TEST_LIST_PRISE);
        retailPriseObject.put(PRICE_KEY, TEST_RETAIL_PRISE);

        saleInfoObject.put(LIST_PRICE, listPriseObject);
        saleInfoObject.put(RETAIL_PRICE, retailPriseObject);

        oneSuccessBookFakeObject.put(SALE_INFO, saleInfoObject);
    }

    @BeforeClass
    private void prepareNotSuccessFakeData() throws JSONException {
        oneNotSuccessBookFakeObject = new JSONObject();
        oneNotSuccessBookFakeObject.put(BOOK_PAGE_URL_KEY, TEST_BOOK_PAGE_URL);

        JSONObject volumeInfoObject = new JSONObject();
        volumeInfoObject.put(TITLE_KEY, TEST_TITLE);
        volumeInfoObject.put(SUBTITLE_KEY, TEST_SUBTITLE);

        JSONArray categories = new JSONArray(TEST_CATEGORIES_RAW);

        volumeInfoObject.put(CATEGORIES_KEY, categories);

        JSONArray authors = new JSONArray(TEST_AUTHORS_RAW);

        volumeInfoObject.put(AUTHOR_KEY, authors);

        JSONObject imagesJsonObject = new JSONObject();
        imagesJsonObject.put(COVER_URL_KEY, TEST_IMAGE_URL);

        volumeInfoObject.put(IMAGES_LINK_KEY, imagesJsonObject);

        oneNotSuccessBookFakeObject.put(VOLUME_INFO_KEY, volumeInfoObject);

        JSONObject saleInfoObject = new JSONObject();
        saleInfoObject.put(IS_FOR_SALE_KEY, NOT_FOR_SALE);

        JSONObject listPriseObject = new JSONObject();
        JSONObject retailPriseObject = new JSONObject();

        listPriseObject.put(PRICE_KEY, TEST_LIST_PRISE);
        retailPriseObject.put(PRICE_KEY, TEST_RETAIL_PRISE);

        saleInfoObject.put(LIST_PRICE, listPriseObject);
        saleInfoObject.put(RETAIL_PRICE, retailPriseObject);

        oneNotSuccessBookFakeObject.put(SALE_INFO, saleInfoObject);
    }



    public void testParseBookSuccessParse() {
        BookDiscountData discountData = parser.parseBook(oneSuccessBookFakeObject);

        Assert.assertEquals(discountData.getBookstoreId().intValue(), GOOGLE_CRAWLER_ID);
        Assert.assertEquals(discountData.getPrice(), TEST_RETAIL_PRISE);
        Assert.assertEquals(discountData.getBookDiscountDetails(), TEST_DISCOUNT_RESULT);

        BookData bookData = discountData.getBookData();

        Assert.assertEquals(bookData.getTitle(), TEST_TITLE);
        Assert.assertEquals(bookData.getSubtitle(), TEST_SUBTITLE);
        Assert.assertEquals(bookData.getAuthors(), TEST_AUTHORS_RESULT);
        Assert.assertEquals(bookData.getGenre(), TEST_CATEGORIES_RESULT);
        Assert.assertEquals(bookData.getCoverUrl(), TEST_IMAGE_URL);
        Assert.assertEquals(bookData.getBookPageUrl(), TEST_BOOK_PAGE_URL);
    }

    public void testParseBooksSuccess() throws JSONException {
        JSONObject mainObject = new JSONObject();

        JSONArray itemsObject = new JSONArray();
        itemsObject.put(oneSuccessBookFakeObject);
        itemsObject.put(oneSuccessBookFakeObject);

        mainObject.put(BOOKS_ARRAY_KEY, itemsObject);

        List<BookDiscountData> booksList = parser.parseBooks(mainObject);
        Assert.assertEquals(booksList.size(), 2);
    }

    public void testParseMixedBooksSuccess() throws JSONException {
        JSONObject mainObject = new JSONObject();

        JSONArray itemsObject = new JSONArray();
        itemsObject.put(oneSuccessBookFakeObject);
        itemsObject.put(oneNotSuccessBookFakeObject);

        mainObject.put(BOOKS_ARRAY_KEY, itemsObject);

        List<BookDiscountData> booksList = parser.parseBooks(mainObject);
        Assert.assertEquals(booksList.size(), 1);
    }

    public void testParseBooksNotSuccess() throws JSONException {
        JSONObject mainObject = new JSONObject();

        JSONArray itemsObject = new JSONArray();
        itemsObject.put(oneNotSuccessBookFakeObject);
        itemsObject.put(oneNotSuccessBookFakeObject);

        mainObject.put(BOOKS_ARRAY_KEY, itemsObject);

        List<BookDiscountData> booksList = parser.parseBooks(mainObject);
        Assert.assertEquals(booksList.size(), 0);
    }
}
