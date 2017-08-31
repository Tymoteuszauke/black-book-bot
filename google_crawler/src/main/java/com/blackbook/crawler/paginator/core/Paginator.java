package com.blackbook.crawler.paginator.core;

/**
 * @author Siarhei Shauchenka
 * @since 17.08.17
 */
public interface Paginator {

     int getNumberOfPages();
     int getItemsOnPage();
     int getCurrentPage();
}
