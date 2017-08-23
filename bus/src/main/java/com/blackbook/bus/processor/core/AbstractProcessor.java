package com.blackbook.bus.processor.core;

import com.blackbook.bus.controller.core.RequestControllerListener;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * @author Siarhei Shauchenka
 * @since 23.08.17
 */
public abstract class AbstractProcessor implements RequestProcessor{

    private RequestControllerListener controllerListener;

    public AbstractProcessor(RequestControllerListener controllerListener) {
        this.controllerListener = controllerListener;
    }

    @Override
    public void run() {
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.post(getProcessorURL()).asJson();
            if (jsonResponse.getStatus() == OK_STATUS) {
                controllerListener.success(getOkMessage());
            } else {
                controllerListener.failed(jsonResponse.getStatusText());
            }
        } catch (UnirestException e) {
            controllerListener.failed(e.getMessage());
        }
    }

    protected abstract String getProcessorURL();
    protected abstract String getOkMessage();
}
