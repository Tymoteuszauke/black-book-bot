package core;

import java.util.concurrent.ExecutorService;

/**
 * @author Sergey Shevchenko
 * @since 16.08.2017
 */
public interface ICrawler {
    void start(CrawlerActionListener actionListener);
    int getId();
}
