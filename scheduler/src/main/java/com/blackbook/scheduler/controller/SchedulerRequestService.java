package com.blackbook.scheduler.controller;

import com.blackbook.scheduler.controller.core.RequestService;
import com.blackbook.scheduler.controller.core.RequestControllerListener;
import com.blackbook.scheduler.model.Observer;
import com.blackbook.scheduler.model.ObserverRepository;
import com.blackbook.scheduler.processor.DataUpdatedNotificationProcessor;
import com.blackbook.scheduler.processor.StartAllCrawlersProcessor;
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
public class SchedulerRequestService implements RequestService {

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
    public SchedulerRequestService(ObserverRepository observerRepository) {
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
