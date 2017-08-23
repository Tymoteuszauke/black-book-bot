package com.blackbook.scheduler.controller.core;

/**
 * @author Siarhei Shauchenka
 * @since 23.08.17
 */
public interface RequestControllerListener {

    void success(String message);
    void failed(String message);
}
