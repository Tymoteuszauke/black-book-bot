package com.blackbook.bus.model;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Siarhei Shauchenka
 * @since 23.08.17
 */
public interface ObserverRepository extends CrudRepository<Observer, Long>{

    Observer findByUrl(String url);
}
