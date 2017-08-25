package com.blackbook.scheduler.controller;

import com.blackbook.scheduler.controller.core.ObserverService;
import com.blackbook.scheduler.model.ObserverCreationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Siarhei Shauchenka
 * @since 23.08.17
 */
@RestController
@RequestMapping(value = "/api/scheduler")
public class SchedulerRestController {


    private SchedulerRequestService schedulerRequestService;
    private ObserverService observerService;

    @Autowired
    public SchedulerRestController(SchedulerRequestService schedulerRequestService, ObserverService observerService) {
        this.schedulerRequestService = schedulerRequestService;
        this.observerService = observerService;
    }

    @RequestMapping(value = "/assign", method = RequestMethod.POST)
    public void assignObserver(@RequestBody ObserverCreationData observerData) {
        observerService.signUpObserver(observerData);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public void removeObserver(@RequestBody ObserverCreationData observerData) {
        observerService.removeObserver(observerData);
    }

    @RequestMapping(value = "/crawlers/finished", method = RequestMethod.POST)
    public void crawlersFinished() {
        schedulerRequestService.dataUpdated();
    }

}
