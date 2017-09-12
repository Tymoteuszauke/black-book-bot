package core;

import view.creationmodel.BookDiscountData;

import java.util.List;

/**
 * @author Sergey Shevchenko
 * @since 16.08.2017
 */
public interface CrawlerActionListener {
    void crawlerFinished(List<BookDiscountData> booksData);
}
