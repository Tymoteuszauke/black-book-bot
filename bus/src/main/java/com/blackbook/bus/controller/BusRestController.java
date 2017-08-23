package com.blackbook.bus.controller;

import com.blackbook.bus.model.Observer;
import com.blackbook.bus.model.ObserverCreationData;
import com.blackbook.bus.model.ObserverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Siarhei Shauchenka
 * @since 23.08.17
 */
@RestController
@RequestMapping(value = "/api/bus")
public class BusRestController {


    private ObserverRepository observerRepository;
    private BusRequestController busRequestController;

    @Autowired
    public BusRestController(ObserverRepository observerRepository, BusRequestController busRequestController) {
        this.observerRepository = observerRepository;
        this.busRequestController = busRequestController;
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
       busRequestController.dataUpdated();
    }

}
