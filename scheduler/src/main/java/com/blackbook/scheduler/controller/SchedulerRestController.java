package com.blackbook.scheduler.controller;

import com.blackbook.scheduler.model.Observer;
import com.blackbook.scheduler.model.ObserverCreationData;
import com.blackbook.scheduler.model.ObserverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Siarhei Shauchenka
 * @since 23.08.17
 */
@RestController
@RequestMapping(value = "/api/scheduler")
public class SchedulerRestController {


    private ObserverRepository observerRepository;
    private SchedulerRequestService schedulerRequestService;

    @Autowired
    public SchedulerRestController(ObserverRepository observerRepository, SchedulerRequestService schedulerRequestService) {
        this.observerRepository = observerRepository;
        this.schedulerRequestService = schedulerRequestService;
    }

    @RequestMapping(value = "/assign", method = RequestMethod.POST)
    public void assignObserver(@RequestBody ObserverCreationData observerData){
        Observer observer = new Observer();
        observer.setUrl(observerData.getUrl());
        observerRepository.save(observer);
    }


    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public void removeObserver(@RequestBody ObserverCreationData observerData){
       Observer observer = observerRepository.findByUrl(observerData.getUrl());
       observerRepository.delete(observer);
    }

    @RequestMapping(value = "/crawlers/finished", method = RequestMethod.POST)
    public void crawlersFinished(){
       schedulerRequestService.dataUpdated();
    }

}
