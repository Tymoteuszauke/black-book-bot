package com.blackbook.googlecrawler.paginator;

import com.blackbook.googlecrawler.paginator.core.Paginator;
import com.blackbook.googlecrawler.paginator.impl.GooglePaginator;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author Siarhei Shauchenka at 07.09.17
 */
@Test
public class GooglePaginatorTest {

    private final int TOTAL_ITEMS = 100;

    private JSONObject fakeObject;
    private Paginator googlePaginator;

    @BeforeClass
    public void prepareFakeObject() throws JSONException {
        fakeObject = new JSONObject();
        fakeObject.put("totalItems", TOTAL_ITEMS);

        googlePaginator = new GooglePaginator(fakeObject);
    }

    public void testGetTotalItems(){
        Assert.assertEquals(googlePaginator.getTotalNumberOfItems(), TOTAL_ITEMS);
    }

    public void testGetItemsOnPage(){
       Assert.assertEquals(googlePaginator.getItemsOnPage(), GooglePaginator.NUMBER_BOOKS_ON_PAGE);
    }

    public void testGetNumberOfPages(){
        int numberOfPages = googlePaginator.getTotalNumberOfItems()/googlePaginator.getItemsOnPage();
        Assert.assertEquals(googlePaginator.getNumberOfPages(), numberOfPages);
    }
}
