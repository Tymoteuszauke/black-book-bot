package com.blackbook.processor;

import com.blackbook.crowler.paginator.core.Paginator;
import org.json.JSONObject;

/**
 * @author Siarhei Shauchenka
 * @since 17.08.17
 */
public interface CrawlerProcessorListener {
    void success(JSONObject requestBody, Paginator paginator);
    void failed(String message);
}
