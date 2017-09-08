package com.blackbook.googlecrawler.processor.core;

import org.json.JSONObject;

/**
 * @author Siarhei Shauchenka at 07.09.17
 */
public interface ResponseListener {
    void onSuccess(JSONObject responseBody);
    void onFailed(String errorMessage);
}
