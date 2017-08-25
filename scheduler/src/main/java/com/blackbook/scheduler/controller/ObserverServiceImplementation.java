package com.blackbook.scheduler.controller;

import com.blackbook.scheduler.controller.core.ObserverService;
import com.blackbook.scheduler.model.Observer;
import com.blackbook.scheduler.model.ObserverCreationData;
import com.blackbook.scheduler.model.ObserverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Shauchenka
 * @since 25.08.17
 */
@Service
public class ObserverServiceImplementation implements ObserverService{


    private ObserverRepository observerRepository;

    @Autowired
    public ObserverServiceImplementation(ObserverRepository observerRepository) {
        this.observerRepository = observerRepository;
    }

    @Override
    public void signUpObserver(ObserverCreationData observerCreationData) {
        Observer observer = new Observer();
        observer.setUrl(observerCreationData.getUrl());
        observerRepository.save(observer);
    }

    @Override
    public void removeObserver(ObserverCreationData observerCreationData) {
        Observer observer = observerRepository.findByUrl(observerCreationData.getUrl());
        observerRepository.delete(observer);
    }
}
