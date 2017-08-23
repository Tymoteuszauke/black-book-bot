package com.blackbook.bus.processor;

import com.blackbook.bus.controller.core.RequestControllerListener;
import com.blackbook.bus.processor.core.AbstractProcessor;

/**
 * @author Siarhei Shauchenka
 * @since 23.08.17
 */
public class DataUpdatedNotificationProcessor extends AbstractProcessor {

    private String notificationUrl;

    public DataUpdatedNotificationProcessor(RequestControllerListener controllerListener, String notificationUrl) {
        super(controllerListener);
        this.notificationUrl = notificationUrl;
    }

    @Override
    protected String getProcessorURL() {
        return notificationUrl;
    }

    @Override
    protected String getOkMessage() {
        return "Observer on: " + notificationUrl + " notified!" ;
    }
}
