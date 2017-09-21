package com.blackbook.googlecrawler.processor.core;

/**
 * @author Siarhei Shauchenka at 15.09.17
 */
public interface RequestCreator<T> {

    void makeRequest();
    boolean isSuccess();
    T getResponseBody();
    String getErrorMessage();
}
