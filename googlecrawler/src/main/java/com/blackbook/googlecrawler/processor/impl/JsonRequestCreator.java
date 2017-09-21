package com.blackbook.googlecrawler.processor.impl;

import com.blackbook.googlecrawler.processor.core.RequestCreator;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.json.JSONObject;

/**
 * @author Siarhei Shauchenka at 15.09.17
 */
@Slf4j
public class JsonRequestCreator implements RequestCreator<JSONObject> {

    private final String requestUrl;
    private boolean isSuccess;
    private JSONObject responseBody;
    private String errorMessage;
    GetRequest getRequest;


    public JsonRequestCreator(String requestUrl) {
        this.requestUrl = requestUrl;
        this.responseBody = new JSONObject();
        this.errorMessage = "";
        this.getRequest = Unirest.get(requestUrl)
                .header("accept", "application/json");
    }

    @Override
    public void makeRequest() {
        try {
            log.info(requestUrl);
            HttpResponse<JsonNode> jsonResponse = getRequest.asJson();
            if (jsonResponse.getStatus() == HttpStatus.SC_OK) {
                isSuccess = true;
                responseBody = jsonResponse.getBody().getObject();
            } else {
                errorMessage = jsonResponse.getStatusText();
            }

        } catch (UnirestException e) {
            log.error(e.getMessage());
            isSuccess = false;
            errorMessage = e.getMessage();
        }
    }

    @Override
    public boolean isSuccess() {
        return isSuccess;
    }

    @Override
    public JSONObject getResponseBody() {
        return responseBody;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
