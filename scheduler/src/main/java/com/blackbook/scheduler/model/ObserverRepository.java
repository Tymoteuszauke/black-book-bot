package com.blackbook.scheduler.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Siarhei Shauchenka
 * @since 23.08.17
 */
public interface ObserverRepository extends CrudRepository<Observer, Long>{

    Observer findByUrl(String url);
    List<Observer> findAll();
}
