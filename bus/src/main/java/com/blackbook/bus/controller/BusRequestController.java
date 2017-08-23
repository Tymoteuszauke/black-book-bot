package com.blackbook.bus.controller;

import com.blackbook.bus.controller.core.RequestController;
import com.blackbook.bus.controller.core.RequestControllerListener;
import com.blackbook.bus.model.Observer;
import com.blackbook.bus.model.ObserverRepository;
import com.blackbook.bus.processor.DataUpdatedNotificationProcessor;
import com.blackbook.bus.processor.StartAllCrawlersProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Siarhei Shauchenka
 * @since 23.08.17
 */
@Service
public class BusRequestController implements RequestController {

    private ObserverRepository observerRepository;

    private ExecutorService executorService;

    private RequestControllerListener requestControllerListener = new RequestControllerListener() {
        public void success(String okMessage) {
            System.out.println(okMessage);
        }

        @Override
        public void failed(String message) {
            System.out.println(message);
        }
    };


    @Autowired
    public BusRequestController(ObserverRepository observerRepository) {
        this.observerRepository = observerRepository;
        this.executorService = Executors.newCachedThreadPool();
    }

    @Override
    public void startCrawlers() {
        executorService.execute(new StartAllCrawlersProcessor(requestControllerListener));
    }

    @Override
    public void dataUpdated() {
        List<Observer> listOfObservers = observerRepository.findAll();
        listOfObservers.forEach((observer) -> executorService.execute(new DataUpdatedNotificationProcessor(requestControllerListener, observer.getUrl())));
    }
}
