package com.blackbook.scheduler.controller;

import com.blackbook.scheduler.controller.core.RequestControllerListener;
import com.blackbook.scheduler.controller.core.RequestService;
import com.blackbook.scheduler.model.Observer;
import com.blackbook.scheduler.model.ObserverRepository;
import com.blackbook.scheduler.processor.DataUpdatedNotificationProcessor;
import com.blackbook.scheduler.processor.StartAllCrawlersProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Siarhei Shauchenka
 * @since 23.08.17
 */
@Slf4j
@Service
@EnableScheduling
public class SchedulerRequestService implements RequestService {

    private ObserverRepository observerRepository;

    private ExecutorService executorService;

    private RequestControllerListener requestControllerListener = new RequestControllerListener() {
        public void success(String okMessage) {
            log.info(okMessage);
        }

        @Override
        public void failed(String message) {
            log.info(message);
        }
    };


    @Autowired
    public SchedulerRequestService(ObserverRepository observerRepository) {
        this.observerRepository = observerRepository;
        this.executorService = Executors.newCachedThreadPool();
    }

    @Scheduled(cron = "0 0 1 * * ?")
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
