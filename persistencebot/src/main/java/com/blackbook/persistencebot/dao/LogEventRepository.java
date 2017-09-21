package com.blackbook.persistencebot.dao;

import com.blackbook.persistencebot.model.LogEventModel;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Siarhei Shauchenka at 11.09.17
 */
public interface LogEventRepository extends CrudRepository<LogEventModel, Long>{
}
