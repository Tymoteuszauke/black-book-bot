package com.blackbook.googlecrawler.paginator;

import com.blackbook.googlecrawler.paginator.core.Paginator;
import com.blackbook.googlecrawler.paginator.impl.DefaultPaginator;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Siarhei Shauchenka at 08.09.17
 */
@Test
public class DefaultPaginatorTest {

    private final static int DEFAULT_NUMBER_OF_PAGES = 1;
    private final static int DEFAULT_NUMBER_ITEMS_ON_PAGE = 20;

    public void testReturnPAgeData(){
        Paginator paginator = new DefaultPaginator();
        Assert.assertEquals(paginator.getNumberOfPages(), DEFAULT_NUMBER_OF_PAGES);
        Assert.assertEquals(paginator.getItemsOnPage(), DEFAULT_NUMBER_ITEMS_ON_PAGE);
        Assert.assertEquals(paginator.getTotalNumberOfItems(), DEFAULT_NUMBER_ITEMS_ON_PAGE);
    }
}
