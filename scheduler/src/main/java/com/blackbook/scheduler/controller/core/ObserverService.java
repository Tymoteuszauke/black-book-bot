package com.blackbook.scheduler.controller.core;

import com.blackbook.scheduler.model.ObserverCreationData;

/**
 * @author Siarhei Shauchenka
 * @since 25.08.17
 */
public interface ObserverService {

    void signUpObserver(ObserverCreationData observerCreationData);
    void removeObserver(ObserverCreationData observerCreationData);
}
