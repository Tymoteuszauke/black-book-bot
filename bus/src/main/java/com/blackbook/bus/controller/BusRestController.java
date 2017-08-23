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

    @Autowired
    private ObserverRepository observerRepository;

    @RequestMapping(value = "/assign", method = RequestMethod.POST)
    public void assignObserver(@RequestBody ObserverCreationData observerData){
        Observer observer = new Observer();
        observer.setUrlForNotification(observerData.getUrl());
        observerRepository.save(observer);
    }
}
